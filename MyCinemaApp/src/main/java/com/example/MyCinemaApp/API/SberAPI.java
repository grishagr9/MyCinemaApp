package com.example.MyCinemaApp.API;

import com.example.MyCinemaApp.config.KeyPassData;
import ru.sberbank.id.sdk.SberApiClient;
import ru.sberbank.id.sdk.Utils;
import ru.sberbank.id.sdk.data.AuthData;
import ru.sberbank.id.sdk.exception.ApiException;

import java.io.IOException;

public class SberAPI {

    public void verify() throws IOException, ApiException {
        SberApiClient client = new SberApiClient(
                KeyPassData.CLIENT_ID, //client_id, нужно поменять на свой
                KeyPassData.CLIENT_SECRET, //client_secret, нужно поменять на свой
                "https://api.sberbank.ru/ru/prod/tokens/v2/oidc", //url на получение access token (для подключений через двусторонний TLS)
//https://sec.api.sberbank.ru/ru/prod/tokens/v2/oidc - для подключений через ФПСУ
                "https://api.sberbank.ru/ru/prod/sberbankid/v2.1/userinfo" //url на получение пользовательских данных (для подключений через двусторонний TLS)
//https://sec.api.sberbank.ru/ru/prod/sberbankid/v2.1/userinfo - для подключений через ФПСУ
        );
        AuthData authData = client.authRequest("http://127.0.0.1:8080/login", //redirect_uri
                        "3C506040-15A9-226B-EFB4-6389A0C0C165", //auth_code
                        "q5P5afVZ1kdehAfbn5XvnCkIfe9kDV1nSRicU8v6efU", //nonce
                        "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk") //code_verifier (опционально)
                .execute();
        String nonce = Utils.randomString();
    }

}
