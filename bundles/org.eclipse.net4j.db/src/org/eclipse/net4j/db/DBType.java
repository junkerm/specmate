/*
 * Copyright (c) 2007-2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Kai Schlamp - bug 282976: [DB] Influence Mappings through EAnnotations
 */
package org.eclipse.net4j.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.net4j.util.io.ExtendedDataInput;
import org.eclipse.net4j.util.io.ExtendedDataOutput;
import org.eclipse.net4j.util.io.ExtendedIOUtil;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.io.TMPUtil;

/**
 * Enumerates the SQL data types that are compatible with the DB framework.
 *
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 */
public enum DBType
{
  BOOLEAN(16)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      return writeValueBoolean(out, resultSet, column, canBeNull);
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      return readValueBoolean(in, statement, column, canBeNull, getCode());
    }
  },

  BIT(-7)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      return writeValueBoolean(out, resultSet, column, canBeNull);
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      return readValueBoolean(in, statement, column, canBeNull, getCode());
    }
  },

  TINYINT(-6)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      byte value = resultSet.getByte(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeByte(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte value = in.readByte();
      statement.setByte(column, value);
      return value;
    }
  },

  SMALLINT(5)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      short value = resultSet.getShort(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeShort(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      short value = in.readShort();
      statement.setShort(column, value);
      return value;
    }
  },

  INTEGER(4)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      int value = resultSet.getInt(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      int value = in.readInt();
      statement.setInt(column, value);
      return value;
    }
  },

  BIGINT(-5)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      long value = resultSet.getLong(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeLong(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      statement.setLong(column, value);
      return value;
    }
  },

  FLOAT(6)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      float value = resultSet.getFloat(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeFloat(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      float value = in.readFloat();
      statement.setFloat(column, value);
      return value;
    }
  },

  REAL(7)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      float value = resultSet.getFloat(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeFloat(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      float value = in.readFloat();
      statement.setFloat(column, value);
      return value;
    }
  },

  DOUBLE(8)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      double value = resultSet.getDouble(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeDouble(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      double value = in.readDouble();
      statement.setDouble(column, value);
      return value;
    }
  },

  NUMERIC(2)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      /** BEGIN SPECMATE PATCH */
      //throw new UnsupportedOperationException("SQL NULL has to be considered");
      BigDecimal value = resultSet.getBigDecimal(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }
      
      BigInteger valueUnscaled = value.unscaledValue();
     
      byte[] byteArray = valueUnscaled.toByteArray();
      out.writeInt(byteArray.length);
      out.write(byteArray);
      out.writeInt(value.scale());
      return value;
      /** END SPECMATE PATCH */
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
    	/** BEGIN SPECMATE PATCH */
        //throw new UnsupportedOperationException("SQL NULL has to be considered");
  		if (canBeNull && !in.readBoolean()) {
  			statement.setNull(column, getCode());
  			return null;
  		}
      	byte[] bytes = in.readByteArray();
         
         int scale = in.readInt();
         BigInteger valueUnscaled = new BigInteger(bytes);
         BigDecimal value = new BigDecimal(valueUnscaled, scale);
        
         // TODO: Read out the precision, scale information and bring the big decimal to the correct form.
         statement.setBigDecimal(column, value);
         return value;
         /** END SPECMATE PATCH */
    }
  },

  DECIMAL(3)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      throw new UnsupportedOperationException("SQL NULL has to be considered");
      // BigDecimal value = resultSet.getBigDecimal(column);
      // BigInteger valueUnscaled = value.unscaledValue();
      //
      // byte[] byteArray = valueUnscaled.toByteArray();
      // out.writeInt(byteArray.length);
      // out.write(byteArray);
      // out.writeInt(value.scale());
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      throw new UnsupportedOperationException("SQL NULL has to be considered");
      // byte[] bytes = in.readByteArray();
      // int scale = in.readInt();
      //
      // BigInteger valueUnscaled = new BigInteger(bytes);
      // BigDecimal value = new BigDecimal(valueUnscaled, scale);
      // statement.setBigDecimal(column, value);
    }
  },

  CHAR(1)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      String value = resultSet.getString(column);
      out.writeString(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      String value = in.readString();
      statement.setString(column, value);
      return value;
    }
  },

  VARCHAR(12)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      String value = resultSet.getString(column);
      out.writeString(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      String value = in.readString();
      statement.setString(column, value);
      return value;
    }
  },

  LONGVARCHAR(-1, "LONG VARCHAR") //$NON-NLS-1$
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      String value = resultSet.getString(column);
      out.writeString(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      String value = in.readString();
      statement.setString(column, value);
      return value;
    }
  },

  CLOB(2005)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      Reader value = null;

      try
      {
        value = resultSet.getCharacterStream(column);
        if (canBeNull)
        {
          if (resultSet.wasNull())
          {
            out.writeBoolean(false);
            return null;
          }

          out.writeBoolean(true);
        }

        return ExtendedIOUtil.writeCharacterStream(out, value);
      }
      finally
      {
        IOUtil.close(value);
      }
    }

    @Override
    public Object readValueWithResult(final ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      ReaderWithLength value = ReaderWithLength.create(in);
      long length = value.getLength();

      statement.setCharacterStream(column, value, (int)length);
      return length;
    }
  },

  DATE(91)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      java.sql.Date value = resultSet.getDate(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      long longValue = value.getTime();
      out.writeLong(longValue);
      return longValue;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      statement.setDate(column, new java.sql.Date(value));
      return value;
    }
  },

  TIME(92)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      java.sql.Time value = resultSet.getTime(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      long longValue = value.getTime();
      out.writeLong(longValue);
      return longValue;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      statement.setTime(column, new java.sql.Time(value));
      return value;
    }
  },

  TIMESTAMP(93)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      java.sql.Timestamp value = resultSet.getTimestamp(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeLong(value.getTime());
      out.writeInt(value.getNanos());
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      long value = in.readLong();
      int nanos = in.readInt();
      java.sql.Timestamp timeStamp = new java.sql.Timestamp(value);
      timeStamp.setNanos(nanos);
      statement.setTimestamp(column, timeStamp);
      return timeStamp;
    }
  },

  BINARY(-2)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      byte[] value = resultSet.getBytes(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value.length);
      out.write(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte[] value = in.readByteArray();
      statement.setBytes(column, value);
      return value;
    }
  },

  VARBINARY(-3)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      byte[] value = resultSet.getBytes(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value.length);
      out.write(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte[] value = in.readByteArray();
      statement.setBytes(column, value);
      return value;
    }
  },

  LONGVARBINARY(-4, "LONG VARBINARY") //$NON-NLS-1$
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      byte[] value = resultSet.getBytes(column);
      if (canBeNull)
      {
        if (resultSet.wasNull())
        {
          out.writeBoolean(false);
          return null;
        }

        out.writeBoolean(true);
      }

      out.writeInt(value.length);
      out.write(value);
      return value;
    }

    @Override
    public Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      byte[] value = in.readByteArray();
      statement.setBytes(column, value);
      return value;
    }
  },

  BLOB(2004)
  {
    @Override
    public Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
    {
      InputStream value = null;

      try
      {
        value = resultSet.getBinaryStream(column);
        if (canBeNull)
        {
          if (resultSet.wasNull())
          {
            out.writeBoolean(false);
            return null;
          }

          out.writeBoolean(true);
        }

        return ExtendedIOUtil.writeBinaryStream(out, value);
      }
      finally
      {
        IOUtil.close(value);
      }
    }

    @Override
    public Object readValueWithResult(final ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
    {
      if (canBeNull && !in.readBoolean())
      {
        statement.setNull(column, getCode());
        return null;
      }

      InputStreamWithLength value = InputStreamWithLength.create(in);
      long length = value.getLength();

      statement.setBinaryStream(column, value, (int)length);
      return length;
    }
  };

  private static final int BOOLEAN_NULL = -1;

  private static final int BOOLEAN_FALSE = 0;

  private static final int BOOLEAN_TRUE = 1;

  private int code;

  private String keyword;

  private DBType(int code, String keyword)
  {
    this.code = code;
    this.keyword = keyword;
  }

  private DBType(int code)
  {
    this(code, null);
  }

  public int getCode()
  {
    return code;
  }

  public String getKeyword()
  {
    return keyword == null ? super.toString() : keyword;
  }

  @Override
  public String toString()
  {
    return getKeyword();
  }

  /**
   * @since 3.0
   */
  public void writeValue(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
  {
    writeValueWithResult(out, resultSet, column, canBeNull);
  }

  /**
   * @since 4.1
   */
  public abstract Object writeValueWithResult(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException;

  /**
   * @since 3.0
   */
  public void readValue(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException
  {
    readValueWithResult(in, statement, column, canBeNull);
  }

  /**
   * @since 4.1
   */
  public abstract Object readValueWithResult(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull) throws SQLException, IOException;

  private static Boolean writeValueBoolean(ExtendedDataOutput out, ResultSet resultSet, int column, boolean canBeNull) throws SQLException, IOException
  {
    boolean value = resultSet.getBoolean(column);
    if (canBeNull)
    {
      if (resultSet.wasNull())
      {
        out.writeByte(BOOLEAN_NULL);
        return null;
      }

      out.writeByte(value ? BOOLEAN_TRUE : BOOLEAN_FALSE);
      return value;
    }

    out.writeBoolean(value);
    return value;
  }

  private static Boolean readValueBoolean(ExtendedDataInput in, PreparedStatement statement, int column, boolean canBeNull, int sqlType)
      throws IOException, SQLException
  {
    if (canBeNull)
    {
      byte opcode = in.readByte();
      switch (opcode)
      {
      case BOOLEAN_NULL:
        statement.setNull(column, sqlType);
        return null;

      case BOOLEAN_FALSE:
        statement.setBoolean(column, false);
        return false;

      case BOOLEAN_TRUE:
        statement.setBoolean(column, true);
        return true;

      default:
        throw new IOException("Invalid boolean opcode: " + opcode);
      }
    }

    boolean value = in.readBoolean();
    statement.setBoolean(column, value);
    return value;
  }

  /**
   * @since 3.0
   */
  public static DBType getTypeByKeyword(String keyword)
  {
    DBType[] values = DBType.values();
    for (int i = 0; i < values.length; i++)
    {
      DBType dbType = values[i];
      if (dbType.getKeyword().equalsIgnoreCase(keyword))
      {
        return dbType;
      }
    }

    return null;
  }

  /**
   * @since 4.2
   */
  public static DBType getTypeByCode(int code)
  {
    DBType[] values = DBType.values();
    for (int i = 0; i < values.length; i++)
    {
      DBType dbType = values[i];
      if (dbType.getCode() == code)
      {
        return dbType;
      }
    }

    return null;
  }

  /**
   * @author Eike Stepper
   */
  private static final class InputStreamWithLength extends FileInputStream
  {
    private final File file;

    private final long length;

    private long pos;

    private InputStreamWithLength(File file, long length) throws FileNotFoundException
    {
      super(file);
      this.file = file;
      this.length = length;
    }

    private void incrementPos(long n) throws IOException
    {
      pos += n;
      if (pos >= length)
      {
        close();
      }
    }

    public long getLength()
    {
      return length;
    }

    @Override
    public int read() throws IOException
    {
      try
      {
        return super.read();
      }
      finally
      {
        incrementPos(1);
      }
    }

    @Override
    public int read(byte[] b) throws IOException
    {
      try
      {
        return super.read(b);
      }
      finally
      {
        incrementPos(b.length);
      }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException
    {
      try
      {
        return super.read(b, off, len);
      }
      finally
      {
        incrementPos(len);
      }
    }

    @Override
    public long skip(long n) throws IOException
    {
      try
      {
        return super.skip(n);
      }
      finally
      {
        incrementPos(n);
      }
    }

    @Override
    public void close() throws IOException
    {
      super.close();
      file.delete();
    }

    public static InputStreamWithLength create(final ExtendedDataInput in) throws IOException
    {
      // new Reader()
      // {
      // @Override
      // public int read(char[] cbuf, int off, int len) throws IOException
      // {
      // return IOUtil.EOF;
      // }
      //
      // @Override
      // public void close() throws IOException
      // {
      // // Do nothing
      // }
      // };

      FileOutputStream fileOutputStream = null;

      try
      {
        File tempFile = TMPUtil.createTempFile("lob_", ".tmp");
        fileOutputStream = new FileOutputStream(tempFile);

        long length = ExtendedIOUtil.readBinaryStream(in, fileOutputStream);

        return new InputStreamWithLength(tempFile, length);
      }
      finally
      {
        IOUtil.close(fileOutputStream);
      }
    }
  }

  /**
   * @author Eike Stepper
   */
  private static final class ReaderWithLength extends FileReader
  {
    private final File file;

    private final long length;

    private long pos;

    private ReaderWithLength(File file, long length) throws FileNotFoundException
    {
      super(file);
      this.file = file;
      this.length = length;
    }

    private void incrementPos(long n) throws IOException
    {
      pos += n;
      if (pos >= length)
      {
        close();
      }
    }

    public long getLength()
    {
      return length;
    }

    @Override
    public int read() throws IOException
    {
      try
      {
        return super.read();
      }
      finally
      {
        incrementPos(1);
      }
    }

    @Override
    public int read(char[] cbuf, int offset, int length) throws IOException
    {
      try
      {
        return super.read(cbuf, offset, length);
      }
      finally
      {
        incrementPos(length);
      }
    }

    @Override
    public int read(CharBuffer target) throws IOException
    {
      try
      {
        return super.read(target);
      }
      finally
      {
        incrementPos(target.length());
      }
    }

    @Override
    public int read(char[] cbuf) throws IOException
    {
      try
      {
        return super.read(cbuf);
      }
      finally
      {
        incrementPos(cbuf.length);
      }
    }

    @Override
    public long skip(long n) throws IOException
    {
      try
      {
        return super.skip(n);
      }
      finally
      {
        incrementPos(n);
      }
    }

    @Override
    public boolean markSupported()
    {
      return false;
    }

    @Override
    public void mark(int readAheadLimit) throws IOException
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public void reset() throws IOException
    {
      throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException
    {
      super.close();
      file.delete();
    }

    public static ReaderWithLength create(final ExtendedDataInput in) throws IOException
    {
      // new Reader()
      // {
      // @Override
      // public int read(char[] cbuf, int off, int len) throws IOException
      // {
      // return IOUtil.EOF;
      // }
      //
      // @Override
      // public void close() throws IOException
      // {
      // // Do nothing
      // }
      // };

      FileWriter fileWriter = null;

      try
      {
        File tempFile = TMPUtil.createTempFile("lob_", ".tmp");
        fileWriter = new FileWriter(tempFile);

        long length = ExtendedIOUtil.readCharacterStream(in, fileWriter);

        return new ReaderWithLength(tempFile, length);
      }
      finally
      {
        IOUtil.close(fileWriter);
      }
    }
  }
}
