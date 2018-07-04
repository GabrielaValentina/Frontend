package com.example.gabri.licentafrontend.Domain.Mappers;

/**
 * Created by gabri on 3/26/2018.
 */

public class UserMapper {

        private String lastName;
        private String firstName;
        private String emailAddress;
        private String password;
        private String phoneNumber;
        private String code;

        public UserMapper() {
        }

        public UserMapper(String lastName, String firstName, String emailAddress,
                           String password, String phoneNumber, String code) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.emailAddress = emailAddress;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.code = code;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


    }

