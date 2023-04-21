package me.iksadnorth.insta.service;

import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.repository.ArticleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RandomRecSys implements RecSystem {
    private final ArticleRepository articleRepository;
    private final Random rand = new Random();

    @Override
    public List<Long> recommend(Long id, Pageable pageable) {
        long count = articleRepository.count();
        int num = Math.min(pageable.getPageSize(), (int) count);
        return new ArrayList<>(sample(count, num));
    }

    private HashSet<Long> sample(long maxNum, int n) {
        if(maxNum < n)
            throw new IllegalArgumentException("n is bigger than maxNum!! n must be less than maxNum");

        HashSet<Long> table = new HashSet<>();
        while(table.size() < n) {
            long element = rand.nextLong(maxNum);
            if(!table.contains(element))
                table.add(element);
        }
        return table;
    }
}
