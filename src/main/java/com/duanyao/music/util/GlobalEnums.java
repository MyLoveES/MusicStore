package com.duanyao.music.util;

public enum GlobalEnums {
    UserSUCCESS(100, "UserSUCCESS"),
    UserServerERROR(101, "UserServerERROR"),
    UserNameEmptyERROR(1100, "UserNameEmptyERROR"),
    UserNameFormatERROR(1101, "UserNameFormatERROR"),
    UserNameExitsERROR(1102, "UserNameExitsERROR"),
    UserNameNotExitsERROR(1103, "UserNameNotExitsERROR"),
    UserPasswordEmptyERROR(1110, "UserPasswordEmptyERROR"),
    UserPasswordFormatERROR(1111, "UserPasswordFormatERROR"),
    UserPasswordWrongERROR(1112, "UserPasswordWrongERROR"),
    UserPhoneFormatERROR(1120, "UserPhoneFormatERROR"),
    UserPhoneExitsERROR(1121, "UserPhoneExitsERROR"),
    UserPhoneNotExitsERROR(1122, "UserPhoneNotExitsERROR"),
    UserEmailFormatERROR(1130, "UserEmailFormatERROR"),
    UserTicketNotExitsERROR(1140, "UserTicketNotExitsERROR"),

    MusicSUCCESS(200, "MusicSUCCESS"),
    MusicServerERROR(201, "MusicServerERROR"),
    MusicIDWrongERROR(2100, "MusicIDWrongERROR"),
    MusicIDFormatERROR(2101, "MusicIDFormatERROR"),
    MusicLocalSaveERROR(2110, "MusicLocalSaveERROR"),
    MusicUploadEmptyERROR(2111, "MusicUploadEmptyERROR"),
    MusicNameEmptyERROR(2100, "MusicNameEmptyERROR"),
    MusicSuffixNameFormatERROR(2101, "MusicSuffixNameFormatERROR");



    private final int code;

    private final String msg;

    GlobalEnums(int code, String msg) {

        this.code = code;

        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public final String getMsg() {
        return msg;
    }


}
