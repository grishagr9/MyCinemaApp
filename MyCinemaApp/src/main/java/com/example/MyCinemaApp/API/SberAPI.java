package com.example.MyCinemaApp.API;

import ru.sberbank.id.sdk.SberApiClient;

public class SberAPI {
    SberApiClient client = new SberApiClient(
            "6a0e103b-1873-4ed5-9903-b4fb51192b23", //client_id, нужно поменять на свой
            "oYBjtOQmQNX4cMAmnKxAnvoGrWeccGKzKxvCKqtm0jQ", //client_secret, нужно поменять на свой
            "https://api.sberbank.ru/ru/prod/tokens/v2/oidc", //url на получение access token (для подключений через двусторонний TLS)
//https://sec.api.sberbank.ru/ru/prod/tokens/v2/oidc - для подключений через ФПСУ
            "https://api.sberbank.ru/ru/prod/sberbankid/v2.1/userinfo" //url на получение пользовательских данных (для подключений через двусторонний TLS)
//https://sec.api.sberbank.ru/ru/prod/sberbankid/v2.1/userinfo - для подключений через ФПСУ
    );
}
