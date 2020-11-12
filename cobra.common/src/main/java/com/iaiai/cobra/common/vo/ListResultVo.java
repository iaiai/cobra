package com.iaiai.cobra.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class ListResultVo<T> extends ResultVo {

    private List<T> result;

}
