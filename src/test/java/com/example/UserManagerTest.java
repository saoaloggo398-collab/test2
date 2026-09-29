package com.example;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demo.User;
import com.example.demo.UserManager;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = UserManager.getInstance();
        userManager.deleteAllUser();
    }

        @Test
        @DisplayName("正常系_UserManagerインスタンス同一")
        void 正常系_UserManagerインスタンス同一() {
            UserManager instance1 = UserManager.getInstance();
            UserManager instance2 = UserManager.getInstance();

            assertThat(instance1)
                    .as("getInstanceを複数回実行した場合、それぞれのUserManagerインスタンスが共通であること")
                    .isSameAs(instance2);
        }

        @Test
        @DisplayName("正常系_userList登録参照")
        void 正常系_userList登録参照() {
            User user1 = new User("U001");
            User user2 = new User("U002");

            userManager.setUserToList(user1);
            userManager.setUserToList(user2);

            assertThat(userManager.getUserList())
                    .as("登録したユーザーが全て取得したListに含まれていること")
                    .contains(user1, user2);
        }

        @Test
        @DisplayName("正常系_userMap登録参照")
        void 正常系_userMap登録参照() {
            User user1 = new User("U001");
            User user2 = new User("U002");

            userManager.setUserToMap(user1);
            userManager.setUserToMap(user2);

            assertThat(userManager.getUserMap().values())
                    .as("登録したユーザーが全て取得したMapに含まれていること")
                    .contains(user1, user2);
        }

        @Test
        @DisplayName("正常系_user全削除")
        void 正常系_user全削除() {
            User user1 = new User("U001");
            userManager.setUserToList(user1);
            userManager.setUserToMap(user1);

            userManager.deleteAllUser();

            assertThat(userManager.getUserList())
                    .as("全削除後にListが空であること")
                    .isEmpty();

            assertThat(userManager.getUserMap())
                    .as("全削除後にMapが空であること")
                    .isEmpty();
        }

        @Test
        @DisplayName("正常系_code指定user削除")
        void 正常系_code指定user削除() {
            User user1 = new User("U001");
            User user2 = new User("U002");

            userManager.setUserToList(user1);
            userManager.setUserToMap(user1);
            userManager.setUserToList(user2);
            userManager.setUserToMap(user2);

            userManager.deleteUser("U001");

            assertThat(userManager.getUserMap())
                    .as("指定した管理コードを持つユーザーがMapから削除されていること")
                    .doesNotContainKey("U001");

            assertThat(userManager.getUserList())
                    .as("指定した管理コードを持つユーザーがListから削除されていること")
                    .doesNotContain(user1);
        }

         @Test
        @DisplayName("異常系_ユーザーコード変更後の削除処理不具合")
        void 異常系_ユーザーコード変更後の削除処理不具合() {
            
            User user = new User("U001");
            userManager.setUserToList(user);
            userManager.setUserToMap(user);

            user.setCode("U999");

            userManager.deleteUser("U999");

            assertThat(userManager.getUserMap().values())
                    .as("コード変更後でもMapから削除されていること")
                    .doesNotContain(user);

            assertThat(userManager.getUserList())
                    .as("コード変更後でもListから削除されていること")
                    .doesNotContain(user);
        }

        @Test
        @DisplayName("正常系_MapList初期生成")
        void 正常系_MapList初期生成() {
            assertThat(userManager.getUserList())
                    .as("取得したListがnullではなく、中身が空であること")
                    .isNotNull()
                    .isEmpty();

            assertThat(userManager.getUserMap())
                    .as("取得したMapがnullではなく、中身が空であること")
                    .isNotNull()
                    .isEmpty();
        }

        @Test
        @DisplayName("正常系_List登録順序保持")
        void 正常系_List登録順序保持() {
            User user1 = new User("U001");
            User user2 = new User("U002");
            User user3 = new User("U003");

            userManager.setUserToList(user1);
            userManager.setUserToList(user2);
            userManager.setUserToList(user3);

            assertThat(userManager.getUserList())
                    .as("複数のユーザーを登録した際、登録した順番でListに格納していること")
                    .containsExactly(user1, user2, user3);
        }

        @Test
        @DisplayName("正常系_Mapキー確認")
        void 正常系_Mapキー確認() {
            User user1 = new User("U001");
            User user2 = new User("U002");

            userManager.setUserToMap(user1);
            userManager.setUserToMap(user2);

            assertThat(userManager.getUserMap())
                    .as("ユーザー管理コードをキーとしてMapに登録していること")
                    .containsEntry("U001", user1)
                    .containsEntry("U002", user2);
        }

    }
