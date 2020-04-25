package co.g2academy.indoapril_1.service.impl;

import co.g2academy.indoapril_1.model.ModelOrder;
import co.g2academy.indoapril_1.model.ModelOrderDetail;
import co.g2academy.indoapril_1.repository.RepositoryBarang;
import co.g2academy.indoapril_1.repository.RepositoryOrder;
import co.g2academy.indoapril_1.repository.RepositoryOrderDetail;
import co.g2academy.indoapril_1.request.RequestOrder;
import co.g2academy.indoapril_1.response.ResponseOrder;
import co.g2academy.indoapril_1.service.ServiceOrder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Repository("ServiceOrder")
public class ServiceOrderImpl implements ServiceOrder {
    private RepositoryBarang repositoryBarang;
    private RepositoryOrderDetail repositoryDetail;
    private RepositoryOrder repositoryOrder;

    //Menambah Data order
    @Override
    @Transactional
    public ResponseOrder create(List<RequestOrder> request){

        Integer total_qty = 0 ;

        Integer id_customer = 0;

        String id_order = UUID.randomUUID().toString();

        String tgl_order = getTanggal();

        for ( RequestOrder data : request ){

            ModelOrderDetail entityDetail = toEntityDetail( id_order, data );

            if ( data.getQty() < 1 ) {

                System.out.println("Qty Tidak Boleh Kurang Dari 1");

                return null;
            }else {

                repositoryDetail.save( entityDetail );

                repositoryBarang.decreaseByOrder(
                        data.getQty(),
                        data.getId_Barang()
                );

                id_customer = data.getId();

                total_qty += data.getQty();
            }

        }

        ModelOrder entityOrder = toEntityOrder(
                id_order,
                total_qty,
                tgl_order,
                id_customer
        );

        repositoryOrder.save( entityOrder );
//        return toResponseOrderSimple(saveEntity);
        return null;
    }
    private ModelOrderDetail toEntityDetail (
            String id_order,
            RequestOrder respon
    ){
        return ModelOrderDetail
                .builder()
                .Id_Order( id_order )
                .Id_Barang( respon.getId_Barang() )
                .Qty_Detail( respon.getQty_Detail() )
                .build();
    }
    private  ModelOrder toEntityOrder (
            String id_order,
            Integer qty_total,
            String tgl_order,
            Integer id_user
    ){
        return ModelOrder
                .builder()
                .Id_Order( id_order )
                .Qty_Total( qty_total )
                .Tgl_Order( tgl_order )
                .Id_User( id_user )
                .build();
    }

//    private ResponseOrder toResponseOrderSimple(ModelOrder entity){
//        return new ResponseOrder(
//                entity.getId_Barang_Masuk(),
//                entity.getTanggal_Masuk());
//    }

    private String getTanggal() {

        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

        Date date = new Date();

        return dateFormat.format(date);
    }
}