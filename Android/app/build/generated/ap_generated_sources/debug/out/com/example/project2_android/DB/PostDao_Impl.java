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
import com.example.project2_android.Entities.Comment;
import com.example.project2_android.Entities.Post;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PostDao_Impl implements PostDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Post> __insertionAdapterOfPost;

  private final EntityInsertionAdapter<Post> __insertionAdapterOfPost_1;

  private final EntityDeletionOrUpdateAdapter<Post> __deletionAdapterOfPost;

  private final EntityDeletionOrUpdateAdapter<Post> __updateAdapterOfPost;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfKeepLatest25Posts;

  public PostDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPost = new EntityInsertionAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Post` (`id`,`like`,`comments`,`name`,`text`,`photo`,`email`,`date`,`userPic`,`isLiked`,`commentsInfo`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getLike());
        statement.bindLong(3, entity.getComments());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        if (entity.getPhoto() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhoto());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getEmail());
        }
        final Long _tmp = Converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        if (entity.getUserPic() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getUserPic());
        }
        final int _tmp_1 = entity.isLiked() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final String _tmp_2 = Converters.fromList(entity.getCommentsInfo());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_2);
        }
      }
    };
    this.__insertionAdapterOfPost_1 = new EntityInsertionAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `Post` (`id`,`like`,`comments`,`name`,`text`,`photo`,`email`,`date`,`userPic`,`isLiked`,`commentsInfo`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getLike());
        statement.bindLong(3, entity.getComments());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        if (entity.getPhoto() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhoto());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getEmail());
        }
        final Long _tmp = Converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        if (entity.getUserPic() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getUserPic());
        }
        final int _tmp_1 = entity.isLiked() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final String _tmp_2 = Converters.fromList(entity.getCommentsInfo());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfPost = new EntityDeletionOrUpdateAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Post` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
      }
    };
    this.__updateAdapterOfPost = new EntityDeletionOrUpdateAdapter<Post>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `Post` SET `id` = ?,`like` = ?,`comments` = ?,`name` = ?,`text` = ?,`photo` = ?,`email` = ?,`date` = ?,`userPic` = ?,`isLiked` = ?,`commentsInfo` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Post entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getLike());
        statement.bindLong(3, entity.getComments());
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        if (entity.getPhoto() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhoto());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getEmail());
        }
        final Long _tmp = Converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp);
        }
        if (entity.getUserPic() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getUserPic());
        }
        final int _tmp_1 = entity.isLiked() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        final String _tmp_2 = Converters.fromList(entity.getCommentsInfo());
        if (_tmp_2 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_2);
        }
        if (entity.getId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM post";
        return _query;
      }
    };
    this.__preparedStmtOfKeepLatest25Posts = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM post WHERE id NOT IN (SELECT id FROM post ORDER BY id DESC LIMIT 25)";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Post... posts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPost.insert(posts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final List<Post> posts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPost.insert(posts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertOrUpdate(final Post... posts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPost_1.insert(posts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertOrUpdate(final List<Post> posts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPost_1.insert(posts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Post post) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPost.handle(post);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Post... posts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPost.handleMultiple(posts);
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
  public void keepLatest25Posts() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfKeepLatest25Posts.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfKeepLatest25Posts.release(_stmt);
    }
  }

  @Override
  public List<Post> index() {
    final String _sql = "SELECT * FROM post";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfLike = CursorUtil.getColumnIndexOrThrow(_cursor, "like");
      final int _cursorIndexOfComments = CursorUtil.getColumnIndexOrThrow(_cursor, "comments");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "photo");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfUserPic = CursorUtil.getColumnIndexOrThrow(_cursor, "userPic");
      final int _cursorIndexOfIsLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLiked");
      final int _cursorIndexOfCommentsInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "commentsInfo");
      final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Post _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final String _tmpPhoto;
        if (_cursor.isNull(_cursorIndexOfPhoto)) {
          _tmpPhoto = null;
        } else {
          _tmpPhoto = _cursor.getString(_cursorIndexOfPhoto);
        }
        _item = new Post(_tmpName,_tmpText,_tmpPhoto);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final int _tmpLike;
        _tmpLike = _cursor.getInt(_cursorIndexOfLike);
        _item.setLike(_tmpLike);
        final int _tmpComments;
        _tmpComments = _cursor.getInt(_cursorIndexOfComments);
        _item.setComments(_tmpComments);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _item.setEmail(_tmpEmail);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = Converters.fromTimestamp(_tmp);
        _item.setDate(_tmpDate);
        final String _tmpUserPic;
        if (_cursor.isNull(_cursorIndexOfUserPic)) {
          _tmpUserPic = null;
        } else {
          _tmpUserPic = _cursor.getString(_cursorIndexOfUserPic);
        }
        _item.setUserPic(_tmpUserPic);
        final boolean _tmpIsLiked;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLiked);
        _tmpIsLiked = _tmp_1 != 0;
        _item.setLiked(_tmpIsLiked);
        final List<Comment> _tmpCommentsInfo;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfCommentsInfo)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfCommentsInfo);
        }
        _tmpCommentsInfo = Converters.fromString(_tmp_2);
        _item.setCommentsInfo(_tmpCommentsInfo);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Post get(final String id) {
    final String _sql = "SELECT * FROM post WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfLike = CursorUtil.getColumnIndexOrThrow(_cursor, "like");
      final int _cursorIndexOfComments = CursorUtil.getColumnIndexOrThrow(_cursor, "comments");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "photo");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfUserPic = CursorUtil.getColumnIndexOrThrow(_cursor, "userPic");
      final int _cursorIndexOfIsLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLiked");
      final int _cursorIndexOfCommentsInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "commentsInfo");
      final Post _result;
      if (_cursor.moveToFirst()) {
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final String _tmpPhoto;
        if (_cursor.isNull(_cursorIndexOfPhoto)) {
          _tmpPhoto = null;
        } else {
          _tmpPhoto = _cursor.getString(_cursorIndexOfPhoto);
        }
        _result = new Post(_tmpName,_tmpText,_tmpPhoto);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final int _tmpLike;
        _tmpLike = _cursor.getInt(_cursorIndexOfLike);
        _result.setLike(_tmpLike);
        final int _tmpComments;
        _tmpComments = _cursor.getInt(_cursorIndexOfComments);
        _result.setComments(_tmpComments);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _result.setEmail(_tmpEmail);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = Converters.fromTimestamp(_tmp);
        _result.setDate(_tmpDate);
        final String _tmpUserPic;
        if (_cursor.isNull(_cursorIndexOfUserPic)) {
          _tmpUserPic = null;
        } else {
          _tmpUserPic = _cursor.getString(_cursorIndexOfUserPic);
        }
        _result.setUserPic(_tmpUserPic);
        final boolean _tmpIsLiked;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLiked);
        _tmpIsLiked = _tmp_1 != 0;
        _result.setLiked(_tmpIsLiked);
        final List<Comment> _tmpCommentsInfo;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfCommentsInfo)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfCommentsInfo);
        }
        _tmpCommentsInfo = Converters.fromString(_tmp_2);
        _result.setCommentsInfo(_tmpCommentsInfo);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Post> getPostsByUser(final String email) {
    final String _sql = "SELECT * FROM post WHERE email = ?";
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
      final int _cursorIndexOfLike = CursorUtil.getColumnIndexOrThrow(_cursor, "like");
      final int _cursorIndexOfComments = CursorUtil.getColumnIndexOrThrow(_cursor, "comments");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
      final int _cursorIndexOfPhoto = CursorUtil.getColumnIndexOrThrow(_cursor, "photo");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfUserPic = CursorUtil.getColumnIndexOrThrow(_cursor, "userPic");
      final int _cursorIndexOfIsLiked = CursorUtil.getColumnIndexOrThrow(_cursor, "isLiked");
      final int _cursorIndexOfCommentsInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "commentsInfo");
      final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Post _item;
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpText;
        if (_cursor.isNull(_cursorIndexOfText)) {
          _tmpText = null;
        } else {
          _tmpText = _cursor.getString(_cursorIndexOfText);
        }
        final String _tmpPhoto;
        if (_cursor.isNull(_cursorIndexOfPhoto)) {
          _tmpPhoto = null;
        } else {
          _tmpPhoto = _cursor.getString(_cursorIndexOfPhoto);
        }
        _item = new Post(_tmpName,_tmpText,_tmpPhoto);
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final int _tmpLike;
        _tmpLike = _cursor.getInt(_cursorIndexOfLike);
        _item.setLike(_tmpLike);
        final int _tmpComments;
        _tmpComments = _cursor.getInt(_cursorIndexOfComments);
        _item.setComments(_tmpComments);
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        _item.setEmail(_tmpEmail);
        final Date _tmpDate;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDate);
        }
        _tmpDate = Converters.fromTimestamp(_tmp);
        _item.setDate(_tmpDate);
        final String _tmpUserPic;
        if (_cursor.isNull(_cursorIndexOfUserPic)) {
          _tmpUserPic = null;
        } else {
          _tmpUserPic = _cursor.getString(_cursorIndexOfUserPic);
        }
        _item.setUserPic(_tmpUserPic);
        final boolean _tmpIsLiked;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsLiked);
        _tmpIsLiked = _tmp_1 != 0;
        _item.setLiked(_tmpIsLiked);
        final List<Comment> _tmpCommentsInfo;
        final String _tmp_2;
        if (_cursor.isNull(_cursorIndexOfCommentsInfo)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getString(_cursorIndexOfCommentsInfo);
        }
        _tmpCommentsInfo = Converters.fromString(_tmp_2);
        _item.setCommentsInfo(_tmpCommentsInfo);
        _result.add(_item);
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
