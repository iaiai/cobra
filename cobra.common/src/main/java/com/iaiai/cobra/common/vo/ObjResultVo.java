package com.iaiai.cobra.common.vo;

import lombok.Data;

@Data
public class ObjResultVo<T> extends ResultVo {

    private T result;

}
