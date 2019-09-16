package com.cj.sshop.domain;

import com.cj.core.v2entity.V2Good;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoFindGood {


    private V2Good good;

    private int num;
}
