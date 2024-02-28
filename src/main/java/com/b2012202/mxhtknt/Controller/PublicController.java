package com.b2012202.mxhtknt.Controller;

import com.b2012202.mxhtknt.Models.NhaTro;
import com.b2012202.mxhtknt.Request.ResponseObject;
import com.b2012202.mxhtknt.Repositories.BinhLuanRepository;
import com.b2012202.mxhtknt.Services.BaiVietService;
import com.b2012202.mxhtknt.Services.BinhLuanService;
import com.b2012202.mxhtknt.Services.NhaTroService;
import com.b2012202.mxhtknt.Services.TuVanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {
    private final BinhLuanRepository binhLuanRepository;
    private final BinhLuanService binhLuanService;
    private final TuVanService tuVanService;
    private final NhaTroService nhaTroService;
    private final BaiVietService baiVietService;

    //Get all bai viet follow by number page
    /**?
     * Get post list by number page and size (number of elements)
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/baiviet")
    public ResponseEntity<ResponseObject> getBaiVietListFollowPage(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(baiVietService.getBaiVietListFollowPage(page, size));
    }
    /**
     * Get custom bai viet by idBaiViet
     */
    @GetMapping("/baiviet/{idBaiViet}")
    public ResponseEntity<ResponseObject> getBaiVietById(@PathVariable Long idBaiViet) {
        return ResponseEntity.ok(baiVietService.getByIdBaiViet(idBaiViet));
    }


    //Get all binh luan of single baiViet
    @GetMapping("/binhluan/{idBaiViet}")
    public ResponseEntity<ResponseObject> getAllBinhLuan(@PathVariable Long idBaiViet) {
        return ResponseEntity.ok(binhLuanService.getAllBinhLuan(idBaiViet));
//        return ResponseEntity.ok(new ResponseObject("ok","Get all comments of post", binhLuanRepository.findByBaiViet_IdBaiViet(idBaiViet)));
    }

    //For test authorization
    @GetMapping("/binhluan/hello")
    public ResponseEntity<ResponseObject> sayHello() {
        return ResponseEntity.ok(new ResponseObject("ok", "Hello this is public request", null));
    }

    /**
     * Get all tu van of the boarding house for single chu tro
     */
    @GetMapping("tuvan/{idChuTro}")
    public ResponseEntity<ResponseObject> getAllTuVanByIdChuTro(@PathVariable Long idChuTro) {
        return ResponseEntity.ok(tuVanService.getAllTuVanByIdChuTro(idChuTro));
    }
    /**
     * Get list of tu van of user
     */
    @GetMapping("tuvan/user/{id}")
    public ResponseEntity<ResponseObject> getAllTuVanByIdUser(@PathVariable Long id) {
        return ResponseEntity.ok(tuVanService.getAllTuVanByIdUser(id));
    }

    //Find nha tro
    @GetMapping("/nhatro")
    public ResponseEntity<ResponseObject> findNhaTroByTenNhaTro(@RequestParam("tenNhaTro") String tenNhaTro,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(nhaTroService.findByTenNhaTro(tenNhaTro, page, size));
    }

    @GetMapping("/nhatro/find-absolute")
    public ResponseEntity<ResponseObject> findNhaTroByAbsoluteAddress(
            @RequestParam("tenDuong") String tenDuong,
            @RequestParam("tenXa") String tenXa,
            @RequestParam("tenHuyen") String tenHuyen,
            @RequestParam("tenTinh") String tenTinh,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(nhaTroService.findNhaTroByAbsoluteAddress(tenDuong, tenXa, tenHuyen, tenTinh, page, size));
    }


}
