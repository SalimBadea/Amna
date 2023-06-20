package com.salem.amna.data.di

import com.salem.amna.data.apiservice.accountinfo.AccountInfoService
import com.salem.amna.data.apiservice.add_product.AddProductServices
import com.salem.amna.data.apiservice.addresses.AddressesServices
import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.apiservice.cart.CartServices
import com.salem.amna.data.apiservice.categories.CategoriesService
import com.salem.amna.data.apiservice.general.GeneralService
import com.salem.amna.data.apiservice.home.HomeServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun bindApiService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun bindHomeService(retrofit: Retrofit): HomeServices {
        return retrofit.create(HomeServices::class.java)
    }

    @Provides
    fun bindGeneralService(retrofit: Retrofit): GeneralService {
        return retrofit.create(GeneralService::class.java)
    }

    @Provides
    fun bindSearchService(retrofit: Retrofit): AddProductServices {
        return retrofit.create(AddProductServices::class.java)
    }


    @Provides
    fun bindAddressService(retrofit: Retrofit): AddressesServices {
        return retrofit.create(AddressesServices::class.java)
    }

    @Provides
    fun bindAccountInfo(retrofit: Retrofit): AccountInfoService {
        return retrofit.create(AccountInfoService::class.java)
    }

    @Provides
    fun bindCategories(retrofit: Retrofit): CategoriesService {
        return retrofit.create(CategoriesService::class.java)
    }
//
//    @Provides
//    fun bindProductService(retrofit: Retrofit): ProductService {
//        return retrofit.create(ProductService::class.java)
//    }

    @Provides
    fun bindCartService(retrofit: Retrofit): CartServices {
        return retrofit.create(CartServices::class.java)
    }

//    @Provides
//    fun bindOrderService(retrofit: Retrofit): OrderService {
//        return retrofit.create(OrderService::class.java)
//    }
//
//    @Provides
//    fun bindFileMangerService(retrofit: Retrofit): FileMangerService {
//        return retrofit.create(FileMangerService::class.java)
//    }
//    @Provides
//    fun bindChatService(retrofit: Retrofit): ChatService {
//        return retrofit.create(ChatService::class.java)
//    }
//    @Provides
//    fun bindNotificationService(retrofit: Retrofit): NotificationService {
//        return retrofit.create(NotificationService::class.java)
//    }
    

}