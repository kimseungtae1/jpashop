package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.web.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    /**
     * 영속성 컨텍스트가 자동 변경(update 쿼리 날리는 행위 필요없음)
     * @param updateItemDto
     */
    @Transactional
    public void updateItem(UpdateItemDto updateItemDto){
        Item item = itemRepository.findOne(updateItemDto.getId());
        item.setName(updateItemDto.getName());
        item.setPrice(updateItemDto.getPrice());
        item.setStockQuantity(updateItemDto.getStockQuantity());
    }
}
