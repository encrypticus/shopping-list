package com.timofeyev.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.timofeyev.shoppinglist.data.ShopListRepositoryImpl
import com.timofeyev.shoppinglist.domain.AddShopItemUseCase
import com.timofeyev.shoppinglist.domain.EditShopItemUseCase
import com.timofeyev.shoppinglist.domain.GetShopItemUseCase
import com.timofeyev.shoppinglist.domain.ShopItem

class ShopItemViewModel: ViewModel() {
  private val repository = ShopListRepositoryImpl

  private val getShopItemUseCase = GetShopItemUseCase(repository)
  private val addShopItemUseCase = AddShopItemUseCase(repository)
  private val editShopItemUseCase = EditShopItemUseCase(repository)

  fun getShopItem(shopItemId: Int) {
    val item = getShopItemUseCase.getShopItem(shopItemId)
  }

  fun addShopItem(shopItem: ShopItem) {
    addShopItemUseCase.addShopItem(shopItem)
  }

  fun editShopItem(shopItem: ShopItem) {
    editShopItemUseCase.editShopItem(shopItem)
  }
}