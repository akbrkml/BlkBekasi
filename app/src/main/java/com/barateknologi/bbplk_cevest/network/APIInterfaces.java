package com.barateknologi.bbplk_cevest.network;

import com.barateknologi.bbplk_cevest.model.ResponseAPI;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by akbar on 19/10/17.
 */

public interface APIInterfaces {

//    @FormUrlEncoded
//    @POST("login")
//    Call<ResponseToken> logIn(
//            @Field("username") String username,
//            @Field("password") String password
//    );

    @GET("cari.php")
    Call<ResponseAPI> updateProfile(
            @Query("idk") String idk
    );

    @POST("edit_foto.php")
    Call<ResponseBody> upload(
            @Body RequestBody file
    );

//    @GET("zoomin/listODP")
//    Call<ResponseApi> getListODP(
//            @Header("Authorization") String auth,
//            @Query("username") String username
//    );
//
//    @GET("zoomin/detail")
//    Call<ResponseApi> getListOnt(
//            @Header("Authorization") String auth,
//            @Query("idzoomin") String idzoomin,
//            @Query("username") String username
//    );
//
//    @GET("user")
//    Call<UserItem> getUser(
//            @Header("Authorization") String auth,
//            @Query("username") String username
//    );
//
//    @GET("profile")
//    Call<ProfileItem> getProfile(
//            @Header("Authorization") String auth,
//            @Query("iduser") int iduser,
//            @Query("username") String username
//    );
//
//    @GET("profile/historyProcess")
//    Call<ResponseApi> getHistoryProcess(
//            @Header("Authorization") String auth,
//            @Query("username") String username
//    );
//
//    @GET("profile/historyFinish")
//    Call<ResponseApi> getHistoryFinish(
//            @Header("Authorization") String auth,
//            @Query("username") String username
//    );
//
//    @GET("profile/voucher")
//    Call<ResponseApi> getVoucher(
//            @Header("Authorization") String auth,
//            @Query("username") String username
//    );
//
//    @GET("profile/detailvoucher")
//    Call<ResponseApi> getDetailVoucher(
//            @Header("Authorization") String auth,
//            @Query("idvoucher") String id,
//            @Query("username") String username
//    );
//
//    @FormUrlEncoded
//    @POST("bantuan")
//    Call<ResponseBody> addBantuan(
//            @Header("Authorization") String auth,
//            @Field("id_user") String id_user,
//            @Field("isibantuan") String isi,
//            @Field("tglterima") String tgl,
//            @Field("username") String username
//    );
//
//    @FormUrlEncoded
//    @POST("zoomin/take")
//    Call<ResponseApi> takeQuest(
//            @Header("Authorization") String auth,
//            @Field("username") String username,
//            @Field("devicesto") String devicesTo,
//            @Field("idzoomintemp") String idZoominTemp,
//            @Field("devicename") String deviceName,
//            @Field("device_id") String deviceId,
//            @Field("witel") String witel,
//            @Field("long") String lon,
//            @Field("lat") String lat,
//            @Field("PORTINSERVICENUMBER") String portInServiceNumber,
//            @Field("DEVICEPORTNUMBER") String devicePortNumber,
//            @Field("ratarata") String rataRata
//    );
//
//    @FormUrlEncoded
//    @POST("zoomin/cancelODP")
//    Call<ResponseBody> cancelOdp(
//            @Header("Authorization") String auth,
//            @Field("case_id") String caseId,
//            @Field("idzoomintemp") String idZoominTemp,
//            @Field("alasan") String alasan,
//            @Field("ket_alasan") String ketAlasan,
//            @Field("username") String username
//    );
//
//    @FormUrlEncoded
//    @POST("zoomin/cancelONT")
//    Call<ResponseBody> cancelOnt(
//            @Header("Authorization") String auth,
//            @Field("case_id") String caseId,
//            @Field("idzoomintemp") String idZoominTemp,
//            @Field("alasan") String alasan,
//            @Field("ket_alasan") String ketAlasan,
//            @Field("username") String username
//    );
//
//    @FormUrlEncoded
//    @POST("zoomin/begin")
//    Call<ResponseBody> beginTransaction(
//            @Header("Authorization") String auth,
//            @Field("case_id") String caseId,
//            @Field("idzoomintemp") String idZoominTemp,
//            @Field("gangguan") String gangguan,
//            @Field("username") String username
//    );
//
//    @FormUrlEncoded
//    @POST("zoomin/finish")
//    Call<ResponseBody> finishTransaction(
//            @Header("Authorization") String auth,
//            @Field("case_id") String caseId,
//            @Field("idzoomintemp") String idZoominTemp,
//            @Field("penyelesaian") String penyelesaian,
//            @Field("status") String status,
//            @Field("material") String material,
//            @Field("perbaikan_data") String perbaikanData,
//            @Field("username") String username
//    );
//
//    @POST("zoomin/uploadImage")
//    Call<ResponseBody> upload(
//            @Header("Authorization") String auth,
//            @Body RequestBody file
//    );
//
//    @POST("zoomin/uploadONT")
//    Call<ResponseBody> uploadONT(
//            @Header("Authorization") String auth,
//            @Body RequestBody file
//    );

    @FormUrlEncoded
    @POST("profile/lucky")
    Call<ResponseBody> changePoint(
            @Header("Authorization") String auth,
            @Field("username") String username,
            @Field("luckypoin") String poin
    );

}