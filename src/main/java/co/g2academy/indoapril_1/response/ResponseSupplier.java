package co.g2academy.indoapril_1.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
public class ResponseSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id_Supplier;
    private String Nama_Supplier;
    private String Alamat_Supplier;
    private String Kota_Supplier;
    private String Provinsi_Supplier;
    private String Telepon_Supplier;
    private String Status;

    public ResponseSupplier (int id_supplier,
                             String nama_supplier,
                             String alamat_supplier,
                             String kota_supplier,
                             String provinsi_supplier,
                             String telepon_supplier,
                             String status){
        this.Id_Supplier = id_supplier;
        this.Nama_Supplier = nama_supplier;
        this.Alamat_Supplier = alamat_supplier;
        this.Kota_Supplier = kota_supplier;
        this.Provinsi_Supplier = provinsi_supplier;
        this.Telepon_Supplier = telepon_supplier;
        this.Status = status;
    }
}
