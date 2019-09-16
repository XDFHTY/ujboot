package com.cj.sshop.service;

import com.cj.core.v2entity.V2Give;

import java.util.List;

public interface GiveService {


    List<V2Give> find0Pay(long userId);
}
