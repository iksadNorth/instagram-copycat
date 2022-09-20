package me.iksadnorth.insta.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.iksadnorth.insta.model.entity.Article;

@Getter
@RequiredArgsConstructor
public enum ColumnType {
    ARTICLE("article"), COMMENT("comment");

    private final String tableName;
}
