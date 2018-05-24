package com.barateknologi.bbplk_cevest.network;

import retrofit2.Callback;

/**
 * Created by akbar on 14/12/17.
 */

public class APIServices {
    private APIInterfaces apiInterface;

    public APIServices() {
        apiInterface = APIClient.builder()
                .create(APIInterfaces.class);
    }

//    public void doLogin(String email, String pass, Callback callback) {
//        apiInterface.logIn(email, pass).enqueue(callback);
//    }
//
//    public void getDataLeader(String auth, String username, Callback callback){
//        apiInterface.getLeader(auth, username).enqueue(callback);
//    }
//
//    public void getListODP(String auth, String username, Callback callback){
//        apiInterface.getListODP(auth, username).enqueue(callback);
//    }
//
//    public void getListONT(String auth, String idzoomin, String username, Callback callback){
//        apiInterface.getListOnt(auth, idzoomin, username).enqueue(callback);
//    }
//
//    public void getUser(String auth, String username, Callback callback){
//        apiInterface.getUser(auth, username).enqueue(callback);
//    }
//
//    public void getProfile(String auth, int iduser, String username, Callback callback){
//        apiInterface.getProfile(auth, iduser, username).enqueue(callback);
//    }
//
//    public void getHistoryProcess(String auth, String username, Callback callback){
//        apiInterface.getHistoryProcess(auth, username).enqueue(callback);
//    }
//
//    public void getHistoryFinish(String auth, String username, Callback callback){
//        apiInterface.getHistoryFinish(auth, username).enqueue(callback);
//    }
//
//    public void getVoucher(String auth, String username, Callback callback){
//        apiInterface.getVoucher(auth, username).enqueue(callback);
//    }
//
//    public void getDetailVoucher(String auth, String id, String username, Callback callback){
//        apiInterface.getDetailVoucher(auth, id, username).enqueue(callback);
//    }
//
//    public void addBantuan(String auth, String id_user, String isibantuan, String tglterima, String username, Callback callback){
//        apiInterface.addBantuan(auth, id_user, isibantuan, tglterima, username).enqueue(callback);
//    }
//
//    public void takeQuest(String auth, String username, String devicesTo, String idZoominTemp,
//                          String deviceName, String deviceId, String witel, String lon,
//                          String lat, String portInServiceNumber, String devicePortNumber, String rataRata,
//                          Callback callback){
//        apiInterface.takeQuest(auth, username, devicesTo, idZoominTemp,
//                deviceName, deviceId, witel, lon,
//                lat, portInServiceNumber, devicePortNumber, rataRata).enqueue(callback);
//    }
//
//    public void cancelOdp(String auth, String caseId, String idZoominTemp,
//                          String alasan, String ketAlasan, String username, Callback callback){
//        apiInterface.cancelOdp(auth, caseId, idZoominTemp, alasan, ketAlasan, username).enqueue(callback);
//    }
//
//    public void cancelOnt(String auth, String caseId, String idZoominTemp,
//                          String alasan, String ketAlasan, String username, Callback callback){
//        apiInterface.cancelOnt(auth, caseId, idZoominTemp, alasan, ketAlasan, username).enqueue(callback);
//    }
//
//    public void beginTransaction(String auth, String caseId, String idZoominTemp, String gangguan, String username, Callback callback){
//        apiInterface.beginTransaction(auth, caseId, idZoominTemp, gangguan, username).enqueue(callback);
//    }
//
//    public void finishTransaction(String auth, String caseId, String idZoominTemp, String penyelesaian,
//                                  String status, String material, String perbaikanData, String username, Callback callback){
//        apiInterface.finishTransaction(auth, caseId, idZoominTemp, penyelesaian, status, material, perbaikanData, username).enqueue(callback);
//    }
//
//    public void changePoint(String auth, String username, String poin, Callback callback){
//        apiInterface.changePoint(auth, username, poin).enqueue(callback);
//    }

}
