package pl.ceph3us.projects.android.ado;

public class Main {

    public static void main(String[] args) {
        try {
            boolean hasArgs = args != null && args.length > 0;
            boolean set =  hasArgs && args[0].equals("s");
            boolean print = hasArgs && args[0].equals("p");
            //
            if(!print) {
                //
                System.out.println("Calculating ANDROID_DAILY_OVERRIDE");
                System.out.println("to print only us 'p' or to set use a 's' arg");
            }
            java.time.LocalDate now = java.time.LocalDate.now();
            java.security.MessageDigest crypt = java.security.MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(String.format(
                    "%1$s:%2$s:%3$s",
                    now.getYear(),
                    now.getMonthValue() - 1,
                    now.getDayOfMonth())
                    .getBytes("utf8"));
            String overrideValue = new java.math.BigInteger(1, crypt.digest()).toString(16);
            System.out.println("ANDROID_DAILY_OVERRIDE is: "+overrideValue);
            if(print) {
                System.out.print(overrideValue);
            } else if(set) {
                System.out.println("exporting ANDROID_DAILY_OVERRIDE");
                //
                Runtime.getRuntime().exec(new String[]{"bash","-c" ,"export ANDROID_DAILY_OVERRIDE="+overrideValue}) ;
                /// Also update the System Variable for current process
                System.setProperty("ANDROID_DAILY_OVERRIDE","overrideValue") ;
                //
                System.out.println("done!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
