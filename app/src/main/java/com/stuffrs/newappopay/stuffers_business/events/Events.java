package com.stuffrs.newappopay.stuffers_business.events;

public class Events {

    public static class AdapterToActivity {

        private String message;

        public AdapterToActivity(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }
}
