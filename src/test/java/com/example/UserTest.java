package com.example;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.User;


public class UserTest {
    
    private static User user;

   @BeforeAll
    static void 前処理() {
         user = new User("U001");
    }

    @Test
    @DisplayName("正常系_ユーザー管理コード登録参照")
    void 正常系_ユーザー管理コード登録参照() {
            user.setCode("U002");

            assertThat(user.getCode())
                    .as("登録したユーザー管理コードと同じ値が取得できること")
                    .isEqualTo("U002");

    }

    @Test
        @DisplayName("正常系_名前登録参照")
        void 正常系_名前登録参照() {
            user.setName("山田太郎");

            assertThat(user.getName())
                    .as("登録した名前と同じ値が取得できること")
                    .isEqualTo("山田太郎");
    }

    @Test
        @DisplayName("正常系_年齢登録参照")
        void 正常系_年齢登録参照() {
            user.setAge(20);

            assertThat(user.getAge())
                    .as("登録した年齢と同じ値が取得できること")
                    .isEqualTo(20);
        }

        @Test
        @DisplayName("異常系_範囲外年齢登録")
        void 異常系_範囲外年齢登録() {
            User user1 = new User("U001");
            user1.setAge(0);

            assertThat(user1.getAge())
                    .as("0歳設定時に既定のエラー値（-1）が取得できること")
                    .isEqualTo(-1);

            User user2 = new User("U002");
            user2.setAge(200);

            assertThat(user2.getAge())
                    .as("200歳設定時に既定のエラー値（-1）が取得できること")
                    .isEqualTo(-1);
        }

        @Test
        @DisplayName("異常系_有効値設定後の範囲外年齢更新不具合")
        void 異常系_年齢登録バグ() {
            user.setAge(25);
            user.setAge(200);

            assertThat(user.getAge())
                    .as("一度有効値を設定後に範囲外の年齢を設定した場合、既定のエラー値（-1）を返すこと")
                    .isEqualTo(-1);
        }
    }