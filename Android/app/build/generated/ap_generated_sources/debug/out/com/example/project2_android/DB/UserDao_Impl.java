package com.example.project2_android.DB;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.project2_android.Entities.User;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser_1;

  private final EntityDeletionOrUpdateAdapter<User> __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `User` (`id`,`firstName`,`lastName`,`email`,`password`,`picture`,`pictureInt`,`friends`,`friendRequests`,`posts`,`displayName`,`userComments`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getFirstName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirstName());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPassword());
        }
        if (entity.getPicture() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPicture());
        }
        statement.bindLong(7, entity.getPictureInt());
        final String _tmp = Converters.fromStringArrayList(entity.getFriends());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = Converters.fromStringArrayList(entity.getFriendRequests());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final String _tmp_2 = Converters.fromStringArrayList(entity.getPosts());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDisplayName());
        }
        final String _tmp_3 = Converters.fromStringArrayList(entity.getUserComments());
        if (_tmp_3 == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp_3);
        }
      }
    };
    this.__insertionAdapterOfUser_1 = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `User` (`id`,`firstName`,`lastName`,`email`,`password`,`picture`,`pictureInt`,`friends`,`friendRequests`,`posts`,`displayName`,`userComments`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getFirstName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirstName());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPassword());
        }
        if (entity.getPicture() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPicture());
        }
        statement.bindLong(7, entity.getPictureInt());
        final String _tmp = Converters.fromStringArrayList(entity.getFriends());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = Converters.fromStringArrayList(entity.getFriendRequests());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final String _tmp_2 = Converters.fromStringArrayList(entity.getPosts());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDisplayName());
        }
        final String _tmp_3 = Converters.fromStringArrayList(entity.getUserComments());
        if (_tmp_3 == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp_3);
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `User` WHERE `email` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getEmail() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getEmail());
        }
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `User` SET `id` = ?,`firstName` = ?,`lastName` = ?,`email` = ?,`password` = ?,`picture` = ?,`pictureInt` = ?,`friends` = ?,`friendRequests` = ?,`posts` = ?,`displayName` = ?,`userComments` = ? WHERE `email` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final User entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getFirstName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirstName());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPassword());
        }
        if (entity.getPicture() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPicture());
        }
        statement.bindLong(7, entity.getPictureInt());
        final String _tmp = Converters.fromStringArrayList(entity.getFriends());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = Converters.fromStringArrayList(entity.getFriendRequests());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final String _tmp_2 = Converters.fromStringArrayList(entity.getPosts());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getDisplayName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getDisplayName());
        }
        final String _tmp_3 = Converters.fromStringArrayList(entity.getUserComments());
        if (_tmp_3 == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp_3);
        }
        if (entity.getEmail() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getEmail());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user";
        return _query;
      }
    };
  }

  @Override
  public void insert(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final List<User> result) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(result);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertOrUpdate(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser_1.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handleMultiple(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handleMultiple(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<User> index() {
    final String _sql = "SELECT * FROM user";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPicture = CursorUtil.getColumnIndexOrThrow(_cursor, "picture");
      final int _cursorIndexOfPictureInt = CursorUtil.getColumnIndexOrThrow(_cursor, "pictureInt");
      final int _cursorIndexOfFriends = CursorUtil.getColumnIndexOrThrow(_cursor, "friends");
      final int _cursorIndexOfFriendRequests = CursorUtil.getColumnIndexOrThrow(_cursor, "friendRequests");
      final int _cursorIndexOfPosts = CursorUtil.getColumnIndexOrThrow(_cursor, "posts");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfUserComments = CursorUtil.getColumnIndexOrThrow(_cursor, "userComments");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final User _item;
        final String _tmpFirstName;
        if (_cursor.isNull(_cursorIndexOfFirstName)) {
          _tmpFirstName = null;
        } else {
          _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        }
        final String _tmpLastName;
        if (_cursor.isNull(_cursorIndexOfLastName)) {
          _tmpLastName = null;
        } else {
          _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpPicture;
        if (_cursor.isNull(_cursorIndexOfPicture)) {
          _tmpPicture = null;
        } else {
          _tmpPicture = _cursor.getString(_cursorIndexOfPicture);
        }
        _item = new User(_tmpFirstName,_tmpLastName,_tmpEmail,_tmpPassword,_tmpPicture);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final int _tmpPictureInt;
        _tmpPictureInt = _cursor.getInt(_cursorIndexOfPictureInt);
        _item.setPictureInt(_tmpPictureInt);
        final ArrayList<String> _tmpFriends;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfFriends)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfFriends);
        }
        _tmpFriends = Converters.fromStringArray(_tmp);
        _item.setFriends(_tmpFriends);
        final ArrayList<String> _tmpFriendRequests;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFriendRequests)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfFriendRequests);
        }
        _tmpFriendRequests = Converters.fromStringArray(_tmp_1);
        _item.setFriendRequests(_tmpFriendRequests);
        final ArrayList<String> _tmpPosts;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfPosts)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfPosts);
        }
        _tmpPosts = Converters.fromStringArray(_tmp_2);
        _item.setPosts(_tmpPosts);
        final String _tmpDisplayName;
        if (_cursor.isNull(_cursorIndexOfDisplayName)) {
          _tmpDisplayName = null;
        } else {
          _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        }
        _item.setDisplayName(_tmpDisplayName);
        final ArrayList<String> _tmpUserComments;
        final String _tmp_3;
        if (_cursor.isNull(_cursorIndexOfUserComments)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getString(_cursorIndexOfUserComments);
        }
        _tmpUserComments = Converters.fromStringArray(_tmp_3);
        _item.setUserComments(_tmpUserComments);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public User get(final String email) {
    final String _sql = "SELECT * FROM user WHERE email = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfPicture = CursorUtil.getColumnIndexOrThrow(_cursor, "picture");
      final int _cursorIndexOfPictureInt = CursorUtil.getColumnIndexOrThrow(_cursor, "pictureInt");
      final int _cursorIndexOfFriends = CursorUtil.getColumnIndexOrThrow(_cursor, "friends");
      final int _cursorIndexOfFriendRequests = CursorUtil.getColumnIndexOrThrow(_cursor, "friendRequests");
      final int _cursorIndexOfPosts = CursorUtil.getColumnIndexOrThrow(_cursor, "posts");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfUserComments = CursorUtil.getColumnIndexOrThrow(_cursor, "userComments");
      final User _result;
      if (_cursor.moveToFirst()) {
        final String _tmpFirstName;
        if (_cursor.isNull(_cursorIndexOfFirstName)) {
          _tmpFirstName = null;
        } else {
          _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        }
        final String _tmpLastName;
        if (_cursor.isNull(_cursorIndexOfLastName)) {
          _tmpLastName = null;
        } else {
          _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpPicture;
        if (_cursor.isNull(_cursorIndexOfPicture)) {
          _tmpPicture = null;
        } else {
          _tmpPicture = _cursor.getString(_cursorIndexOfPicture);
        }
        _result = new User(_tmpFirstName,_tmpLastName,_tmpEmail,_tmpPassword,_tmpPicture);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final int _tmpPictureInt;
        _tmpPictureInt = _cursor.getInt(_cursorIndexOfPictureInt);
        _result.setPictureInt(_tmpPictureInt);
        final ArrayList<String> _tmpFriends;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfFriends)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfFriends);
        }
        _tmpFriends = Converters.fromStringArray(_tmp);
        _result.setFriends(_tmpFriends);
        final ArrayList<String> _tmpFriendRequests;
        final String _tmp_1;
        if (_cursor.isNull(_cursorIndexOfFriendRequests)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getString(_cursorIndexOfFriendRequests);
        }
        _tmpFriendRequests = Converters.fromStringArray(_tmp_1);
        _result.setFriendRequests(_tmpFriendRequests);
        final ArrayList<String> _tmpPosts;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfPosts)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfPosts);
        }
        _tmpPosts = Converters.fromStringArray(_tmp_2);
        _result.setPosts(_tmpPosts);
        final String _tmpDisplayName;
        if (_cursor.isNull(_cursorIndexOfDisplayName)) {
          _tmpDisplayName = null;
        } else {
          _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        }
        _result.setDisplayName(_tmpDisplayName);
        final ArrayList<String> _tmpUserComments;
        final String _tmp_3;
        if (_cursor.isNull(_cursorIndexOfUserComments)) {
          _tmp_3 = null;
        } else {
          _tmp_3 = _cursor.getString(_cursorIndexOfUserComments);
        }
        _tmpUserComments = Converters.fromStringArray(_tmp_3);
        _result.setUserComments(_tmpUserComments);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
