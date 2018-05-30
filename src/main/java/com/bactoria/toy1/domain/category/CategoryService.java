package com.bactoria.toy1.domain.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class CategoryService {

        private CategoryRepository categoryRepository;

        public List<Category> resCategory() {
            return categoryRepository.findAll();
        }

        public void saveCategory (CategorySaveRequestDto dto) {
            categoryRepository.save(dto.toEntity());
        }

        public void deleteCategory (Long id) {
            categoryRepository.deleteById(id);
        }

}
