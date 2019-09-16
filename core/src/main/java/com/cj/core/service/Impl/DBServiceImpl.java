package com.cj.core.service.Impl;

import com.cj.core.domain.Datapram;
import com.cj.core.service.DBService;
import com.cj.core.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBServiceImpl implements DBService {

    @Autowired
    private Datapram datapram;

    private final String db1 = "ujie";
    private final String db2 = "msg_data";
    @Override
    public void backup() {
        String string = datapram.getTooldir()
                +" -h "+datapram.getDbip() +" -P"+datapram.getDbport()
                +" -u"+datapram.getUsername()+" -p"+datapram.getPassword()+" ";

        SqlUtil.backup(string,datapram.getDir(),db1);
//        SqlUtil.backup(string,datapram.getDir(),db2);
    }
}
