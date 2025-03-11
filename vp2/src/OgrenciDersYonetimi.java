import java.util.Scanner;


class OgrenciNode {
    int ogrenciNo;
    DersNode dersBas;
    OgrenciNode sonrakiOgrenci;

    public OgrenciNode(int ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
        this.dersBas = null;
        this.sonrakiOgrenci = null;
    }
}
class DersNode {
    String dersKodu;
    OgrenciNode ogrenciBas;
    DersNode sonrakiDers;

    public DersNode(String dersKodu) {
        this.dersKodu = dersKodu;
        this.ogrenciBas = null;
        this.sonrakiDers = null;
    }
}


public class OgrenciDersYonetimi {
    private static OgrenciNode ogrenciBas = null;
    private static DersNode dersBas = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n---- Öğrenci - Ders Yönetimi ----");
            System.out.println("1- Öğrenciye Ders Ekle");
            System.out.println("2- Derse Öğrenci Ekle");
            System.out.println("3- Öğrencinin Dersini Sil");
            System.out.println("4- Dersteki Öğrenciyi Sil");
            System.out.println("5- Dersteki Öğrencileri Listele");
            System.out.println("6- Öğrencinin Derslerini Listele");
            System.out.println("7- Çıkış");
            System.out.print("Seçiminiz: ");

            int secim = scanner.nextInt();
            scanner.nextLine();

            switch (secim) {
                case 1 -> ogrenciyeDersEkle();
                case 2 -> derseOgrenciEkle();
                case 3 -> ogrencininDersiniSil();
                case 4 -> derstekiOgrenciyiSil();
                case 5 -> dersOgrencileriniListele();
                case 6 -> ogrencininDersleriniListele();
                case 7 -> {
                    System.out.println("Programdan çıkılıyor...");
                    return;
                }
                default -> System.out.println("Geçersiz seçim!");
            }
        }
    }

    private static void derseOgrenciEkle() {
        System.out.print("Ders Kodu: ");
        String dersKodu = scanner.nextLine();

        System.out.print("Öğrenci No: ");
        int ogrNo = scanner.nextInt();
        scanner.nextLine();

        DersNode ders = dersBul(dersKodu);
        OgrenciNode ogrenci = ogrenciBul(ogrNo);

        if (ders == null) {
            System.out.println("Ders bulunamadı!");
            return;
        }
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı!");
            return;
        }


        DersNode yeniDers = new DersNode(dersKodu);
        yeniDers.sonrakiDers = ogrenci.dersBas;
        ogrenci.dersBas = yeniDers;


        OgrenciNode yeniOgrenci = new OgrenciNode(ogrNo);
        yeniOgrenci.sonrakiOgrenci = ders.ogrenciBas;
        ders.ogrenciBas = yeniOgrenci;

        System.out.println("Öğrenci derse eklendi!");
    }

    private static void derstekiOgrenciyiSil() {
        System.out.print("Ders Kodu: ");
        String dersKodu = scanner.nextLine();

        System.out.print("Silinecek Öğrenci No: ");
        int ogrNo = scanner.nextInt();
        scanner.nextLine();

        DersNode ders = dersBul(dersKodu);
        if (ders == null || ders.ogrenciBas == null) {
            System.out.println("Ders veya öğrenci bulunamadı!");
            return;
        }

        if (ders.ogrenciBas.ogrenciNo == ogrNo) {
            ders.ogrenciBas = ders.ogrenciBas.sonrakiOgrenci;
        } else {
            OgrenciNode temp = ders.ogrenciBas;
            while (temp.sonrakiOgrenci != null && temp.sonrakiOgrenci.ogrenciNo != ogrNo) {
                temp = temp.sonrakiOgrenci;
            }
            if (temp.sonrakiOgrenci != null) {
                temp.sonrakiOgrenci = temp.sonrakiOgrenci.sonrakiOgrenci;
            } else {
                System.out.println("Öğrenci derste bulunamadı!");
                return;
            }
        }
        System.out.println("Öğrenci dersten silindi!");
    }


    private static void ogrenciyeDersEkle() {
        System.out.print("Öğrenci No: ");
        int ogrNo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ders Kodu: ");
        String dersKodu = scanner.nextLine();

        OgrenciNode ogrenci = ogrenciBul(ogrNo);
        DersNode ders = dersBul(dersKodu);

        if (ogrenci == null) {
            ogrenci = new OgrenciNode(ogrNo);
            ogrenci.sonrakiOgrenci = ogrenciBas;
            ogrenciBas = ogrenci;
        }

        if (ders == null) {
            ders = new DersNode(dersKodu);
            ders.sonrakiDers = dersBas;
            dersBas = ders;
        }

        DersNode yeniDers = new DersNode(dersKodu);
        yeniDers.sonrakiDers = ogrenci.dersBas;
        ogrenci.dersBas = yeniDers;

        OgrenciNode yeniOgrenci = new OgrenciNode(ogrNo);
        yeniOgrenci.sonrakiOgrenci = ders.ogrenciBas;
        ders.ogrenciBas = yeniOgrenci;

        System.out.println("Ders başarıyla eklendi!");
    }

    private static OgrenciNode ogrenciBul(int ogrNo) {
        OgrenciNode temp = ogrenciBas;
        while (temp != null) {
            if (temp.ogrenciNo == ogrNo) return temp;
            temp = temp.sonrakiOgrenci;
        }
        return null;
    }

    private static DersNode dersBul(String dersKodu) {
        DersNode temp = dersBas;
        while (temp != null) {
            if (temp.dersKodu.equals(dersKodu)) return temp;
            temp = temp.sonrakiDers;
        }
        return null;
    }

    private static void dersOgrencileriniListele() {
        System.out.print("Ders Kodu: ");
        String dersKodu = scanner.nextLine();
        DersNode ders = dersBul(dersKodu);

        if (ders == null) {
            System.out.println("Ders bulunamadı!");
            return;
        }

        System.out.println(dersKodu + " dersini alan öğrenciler:");
        OgrenciNode temp = ders.ogrenciBas;
        while (temp != null) {
            System.out.println("- " + temp.ogrenciNo);
            temp = temp.sonrakiOgrenci;
        }
    }

    private static void ogrencininDersleriniListele() {
        System.out.print("Öğrenci No: ");
        int ogrNo = scanner.nextInt();
        scanner.nextLine();

        OgrenciNode ogrenci = ogrenciBul(ogrNo);
        if (ogrenci == null) {
            System.out.println("Öğrenci bulunamadı!");
            return;
        }

        System.out.println(ogrNo + " numaralı öğrencinin dersleri:");
        DersNode temp = ogrenci.dersBas;
        while (temp != null) {
            System.out.println("- " + temp.dersKodu);
            temp = temp.sonrakiDers;
        }
    }

    private static void ogrencininDersiniSil() {
        System.out.print("Öğrenci No: ");
        int ogrNo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Silinecek Ders Kodu: ");
        String dersKodu = scanner.nextLine();

        OgrenciNode ogrenci = ogrenciBul(ogrNo);
        if (ogrenci == null || ogrenci.dersBas == null) {
            System.out.println("Öğrenci veya ders bulunamadı!");
            return;
        }

        if (ogrenci.dersBas.dersKodu.equals(dersKodu)) {
            ogrenci.dersBas = ogrenci.dersBas.sonrakiDers;
        } else {
            DersNode temp = ogrenci.dersBas;
            while (temp.sonrakiDers != null && !temp.sonrakiDers.dersKodu.equals(dersKodu)) {
                temp = temp.sonrakiDers;
            }
            if (temp.sonrakiDers != null) temp.sonrakiDers = temp.sonrakiDers.sonrakiDers;
        }
        System.out.println("Ders silindi!");
    }
}