package com.senzecit.iitiimshaadi.payment;


/**
 * Created by Rahul Hooda on 14/7/17.
 */
public enum AppEnvironment {

    SANDBOX {
        @Override
        public String merchant_Key() {
            return "cA22SFFD";
        }

        @Override
        public String merchant_ID() {
            return "393463";
        }

        @Override
        public String furl() {
            return "https://www.payumoney.com/mobileapp/payumoney/failure.php";
        }

        @Override
        public String surl() {
            return "https://iitiimshaadi.com/members/paymentSuccess";
        }

        @Override
        public String salt() {
            return "qi1nH3DsQl";
        }

        @Override
        public boolean debug() {
            return true;
        }
    },
    PRODUCTION {
        @Override
        public String merchant_Key() {
            return "YhZXAj";
//            return "O15vkB";
        }

        @Override
        public String merchant_ID() {
            return "5259474";
        }

        @Override
        public String furl() {
            return "https://iitiimshaadi.com/_payment";
        }

        @Override
        public String surl() {
            return "https://iitiimshaadi.com/members/paymentSuccess";
        }

        @Override
        public String salt() {
            return "shUO0FaL";
        }

        @Override
        public boolean debug() {
            return false;
        }
    };

    public abstract String merchant_Key();

    public abstract String merchant_ID();

    public abstract String furl();

    public abstract String surl();

    public abstract String salt();

    public abstract boolean debug();


}
