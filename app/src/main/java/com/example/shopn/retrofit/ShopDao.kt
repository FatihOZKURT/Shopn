package com.example.shopn.retrofit


import com.example.shopn.data.entity.CRUDResponse
import com.example.shopn.data.entity.CartItem
import com.example.shopn.data.entity.CartResponse
import com.example.shopn.data.entity.ProductsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ShopDao {

    @GET("urunler/tumUrunleriGetir.php")
    suspend fun loadProducts(): ProductsResponse

    @FormUrlEncoded
    @POST("urunler/sepeteUrunEkle.php")
    suspend fun addToCart(
        @Field("ad") ad: String,
        @Field("resim") resim: String,
        @Field("kategori") kategori: String,
        @Field("fiyat") fiyat: Int,
        @Field("marka") marka: String,
        @Field("siparisAdeti") siparisAdeti: Int,
        @Field("kullaniciAdi") kullaniciAdi: String
    ): CRUDResponse


    @FormUrlEncoded
    @POST("urunler/sepettekiUrunleriGetir.php")
    suspend fun getCartItems(
        @Field("kullaniciAdi") kullaniciAdi: String
    ): CartResponse

    @FormUrlEncoded
    @POST("urunler/sepettenUrunSil.php")
    suspend fun deleteFromCart(
        @Field("sepetId") sepetId: Int,
        @Field("kullaniciAdi") kullaniciAdi: String
    ): CRUDResponse


}