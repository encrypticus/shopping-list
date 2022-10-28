package com.timofeyev.shoppinglist.domain

import androidx.lifecycle.LiveData
import java.util.*

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
  fun getShopList(): LiveData<List<ShopItem>> {
    return shopListRepository.getShopList()
  }
}