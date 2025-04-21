package com.example.lv1_a.model;

public class PersonServiceResponse {

        private String status;
        private Person data;

        // Konstruktor
        public PersonServiceResponse(String status, Person data) {
            this.status = status;
            this.data = data;
        }

        // Getter und Setter
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public Person getData() {
            return data;
        }
        public void setData(Person data) {
            this.data = data;
        }

}
