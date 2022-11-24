package com.timofeyev.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.timofeyev.shoppinglist.R
import com.timofeyev.shoppinglist.domain.ShopItem

/**
 * Последовательность создания адаптера для RecyclerView:
 * 1. Создать класс-наследник от ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()),
 *    где ShopItem - это наша data-класс модели, ShopItemViewHolder(название класса - любое) - это
 *    класс-наследник RecyclerView.ViewHolder(view), а ShopItemDiffCallback - наследник DiffUtil.ItemCallback.
 *
 * 2. Создать класс ShopItemViewHolder - наследник от RecyclerView.ViewHolder(view).
 *
 * 3. Переопределить метод onCreateViewHolder, внутри которого создать view из xml-файла макета
 *    элемента списка через LayoutInflater и вернуть
 *    объект класса ShopItemViewHolder, передав ему в конструктор созданный view - return ShopItemViewHolder(view).
 *
 * 4. Так как на предыдущем шаге в методе onCreateViewHolder мы веренули объект класса
 *    ShopItemViewHolder и передали ему view,
 *    созданный из xml-файла макета элемента списка, то внутри класса ShopItemViewHolder мы можем
 *    выполнять поиск элементов, имеющихся внутри этого макета,
 *    например: val tvName = view.findViewById<TextView>(R.id.tv_name).
 *
 * 5. Переопределить метод onBindViewHolder. Так как метод onBindViewHolder вызывается для
 *    отображения элемента в указанной позиции и по сути вызвыается
 *    каждый раз при скроллинге списка, то внутри него мы можем производить различные манипуляции с view элемента
 *    списка: устанавливать ему текст, цвет, изображение и т.д.
 *    в зависимости от условий.Метод onViewRecycled вызывается всякий раз, когда элемент переиспользуется, то есть
 *    по сути при скроллинге.
 */

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

  var count = 0

  var onShopItemLongClickListener: ((shopItem: ShopItem) -> Unit)? = null
  var onShopItemClickListener: ((shopItem: ShopItem) -> Unit)? = null

  /**
   * Этот метод создает новый объект ViewHolder всякий раз, когда RecyclerView нуждается в этом.
   * Это тот момент, когда создаётся layout строки списка, передается объекту ViewHolder,
   * и каждый дочерний view-компонент может быть найден и сохранен.
   */
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
    val layout = when (viewType) {
      VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
      VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
      else -> throw RuntimeException("Unknown viewType")
    }

    val view = LayoutInflater.from(parent.context).inflate(
      layout,
      parent,
      false
    )
    return ShopItemViewHolder(view)
  }

  /**
   * Этот метод принимает объект ViewHolder и устанавливает необходимые данные для соответствующей
   * строки во view-компоненте.
   */
  override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
    val shopItem = getItem(position)

    holder.itemView.setOnClickListener {
      onShopItemClickListener?.invoke(shopItem)
    }

    holder.itemView.setOnLongClickListener {
      onShopItemLongClickListener?.invoke(shopItem)
      true
    }

    holder.tvName.text = "${shopItem.name}"
    holder.tvCount.text = shopItem.count.toString()
  }

  /**
   * Данный метод необходимо переопределять, если для разных элементов списка нужно использовать
   * разные макеты. Числовое значение, возвращаемое из метода будет передаваться как аргумент
   * методу onCreateViewHolder в параметр viewType
   */
  override fun getItemViewType(position: Int): Int {
    val shopItem = getItem(position)
    return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
  }

  companion object {
    const val VIEW_TYPE_ENABLED = 1
    const val VIEW_TYPE_DISABLED = 0

    /**
     * RecyclerView не может переиспользовать ViewHolder, если его тип отличается от типа элемента,
     * который нужно переиспользовать. Пулл вьюхолдеров имеет ограниченный размер. Если он заполнен,
     * то при добавлении новых вьюхолдеров, старые будут удаляться. Размер пула вьюхолдеров можно
     * увеличить для каждого viewType (см. MainActivity.kt, строки 29,30)
     */
    const val MAX_POOL_SIZE = 10
  }

}