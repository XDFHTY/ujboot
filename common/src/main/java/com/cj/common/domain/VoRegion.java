package com.cj.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoRegion {

    private Long id;

    private String name;

    private String code;

    private List<VoRegion> child;
}
