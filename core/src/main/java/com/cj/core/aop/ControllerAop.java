package com.cj.core.aop;

import com.cj.core.domain.ApiResult;
import com.cj.core.entity.OperateLog;
import com.cj.core.util.IpUtil;
import com.cj.core.util.JsonUtil;
import com.cj.core.util.ObjectUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.UUID;

/**
 * function 系统访问数据信息增强工具类-aop
 * Created by duyuxiang on 2017/9/22.
 * version v1.0
 */
@SuppressWarnings("ALL")
@Component
@Aspect
@Slf4j
public class ControllerAop {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Gson gson = JsonUtil.gson;



        //匹配com.cj.tangtuan.controller包及其子包下的所有类的所有方法
    @Pointcut("execution(* com.cj.*.controller.*..*(..))")
    public void executeService() {
    }


    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("收到请求：Around ====================================================");

        long start = System.currentTimeMillis();
        OperateLog operateLog = new OperateLog();
        try {
            Object result = joinPoint.proceed();
            if (result == null) {
                //如果切到了 没有返回类型的void方法，这里直接返回
                return null;
            }
            long end = System.currentTimeMillis();

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return result;
            }
            HttpServletRequest request = attributes.getRequest();
            StringBuffer Url = request.getRequestURL();
            if (Url.indexOf("/api/") == -1 || Url.indexOf("/ws") != -1) {
                return result;
            }

            Long id = null;
            if (request.getHeader("id")!=null)id = Long.parseLong((request.getHeader("id")));

            operateLog.setOperateId(id);

            String name = (String) request.getSession().getAttribute("name");

            //获取IP
            String ip = IpUtil.getIpAddr(request);


            Object[] args = joinPoint.getArgs();// 参数
            int argsSize = args.length;
            String argsTypes = "";

            String typeStr = joinPoint.getSignature().getDeclaringType().toString().split(" ")[0];
            String returnType = joinPoint.getSignature().toString().split(" ")[0];


            // 记录下请求内容
            log.info("请求 URL : " + request.getRequestURL().toString());
            log.info("IP  地址 : " + ip);
            log.info("请求方式 : " + request.getMethod());
            log.info("请求时间 : " + sdf.format(start));
            log.info("请求用户 : " + name);


//            log.info("参数个数 :" + argsSize);

//            if (argsSize > 0) {
//                // 拿到参数的类型
//                for (Object object : args) {
//                    if (object != null) {
//                        argsTypes += object.getClass().getTypeName().toString() + ", ";
//                    }
//                }
//                log.info("参数类型 :" + argsTypes);
//            }
            log.info("执行方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.info("执行参数 : " + Arrays.toString(joinPoint.getArgs()));

//            log.info("返回类型 :" + returnType);

//            //获取所有参数方法一：
//            Enumeration<String> enu=header.getParameterNames();
//            while(enu.hasMoreElements()){
//                String paraName=(String)enu.nextElement();
//                System.out.println("======================"+paraName);
//            }


            operateLog = getControllerMethodDescription(joinPoint,operateLog);
            log.info("执    行 : " + operateLog.getLogName()+" =====> "+operateLog.getLogValue());

            Long total = end - start;
            // 处理完请求，返回内容
            if ("ApiResult".equals(returnType)) {
                ApiResult apiResult = (ApiResult) result;
                log.info("执行结果 : " + apiResult.getMsg());
                operateLog.setLogState(apiResult.getMsg());
//                log.info("返回数据 : " + JsonUtil.gson.toJson(apiResult.getData()));
//                apiResult.setParams("耗时："+total+"ms");
//                result = ObjectUtil.mapToObject(apiResult.toMap(), ApiResult.class);
            }

            if (!request.getMethod().equals("GET") && operateLog.getOperateId()!=null){

                OperateLog finalOperateLog = operateLog;
                new Thread(()->{
                    CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
                    rabbitTemplate.convertAndSend("ExLog","key-log", finalOperateLog,correlationId);
                }).start();

            }

            log.info("Aop耗时  : " + total + " ms!");
            log.info("执行完成 :==========================================================================================");


            return result;

        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            log.info("====around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : "
                    + e.getMessage());
            throw e;
        }

    }

    @Before("executeService()")
    public void doBefore(JoinPoint joinPoint) {

    }

    @AfterReturning(value = "executeService()", returning = "obj")
    public void doAfterReturning(JoinPoint joinPoint, Object obj) {


    }

    // 通过反射获取参入的参数
    public static OperateLog getControllerMethodDescription(JoinPoint joinPoint,OperateLog operateLog) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        String name = "";
        String value = "";

        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();

                if (clazzs.length == arguments.length) {
                    if (method.getAnnotation(Log.class) != null) {
                        name = method.getAnnotation(Log.class).name();
                        value = method.getAnnotation(Log.class).value();
                    }
                    break;
                }
            }
        }
        operateLog.setLogName(name);
        operateLog.setLogValue(value);
        return operateLog;
    }




}
