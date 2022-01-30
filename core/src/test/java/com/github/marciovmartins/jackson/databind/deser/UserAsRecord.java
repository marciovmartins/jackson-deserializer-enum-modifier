package com.github.marciovmartins.jackson.databind.deser;

record UserAsRecord(int id, String name, Type type) {
    enum Type {
        REGULAR, PREMIUM
    }
}
