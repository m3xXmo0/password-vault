package de.mo.vault;

public class Main {
    public static void main (String[] args){

        Vault vault = new Vault();
        vault.add(new Entry("Netflix", "Maxi", "passwort123"));
        vault.add(new Entry("GitHub", "m3xXmo0", "test1234"));

        for (Entry entry : vault.getEntries()){
            System.out.println(entry);
        }
        Entry found = vault.findByService("GitHub");
        System.out.println(found);
        System.out.println(vault.findByService("Spotify"));
    }


}
