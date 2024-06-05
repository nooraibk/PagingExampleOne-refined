package com.example.pagingexampleone.core.utils

enum class DataType(dataCode : Int) {
    NONE(-1), Local(0), Network(1), Mediator(2);
    companion object{
        fun toDataTypeEnum(dataCode : Int) : DataType {
            return when (dataCode) {
                0 -> Local
                1 -> Network
                2 -> Mediator
                else -> NONE
            }
        }
    }
}