package com.zongyou.library.util.log;

public abstract interface Logger
{
  public static final int V = 2;
  public static final int D = 3;
  public static final int I = 4;
  public static final int W = 5;
  public static final int E = 6;
  public static final int A = 7;

  public abstract void v(String paramString1, String paramString2);

  public abstract void v(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void v(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract void v(String paramString1, Throwable paramThrowable, String paramString2, Object[] paramArrayOfObject);

  public abstract void d(String paramString1, String paramString2);

  public abstract void d(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void d(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract void d(String paramString1, Throwable paramThrowable, String paramString2, Object[] paramArrayOfObject);

  public abstract void i(String paramString1, String paramString2);

  public abstract void i(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void i(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract void i(String paramString1, Throwable paramThrowable, String paramString2, Object[] paramArrayOfObject);

  public abstract void w(String paramString1, String paramString2);

  public abstract void w(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void w(String paramString, Throwable paramThrowable);

  public abstract void w(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract void w(String paramString1, Throwable paramThrowable, String paramString2, Object[] paramArrayOfObject);

  public abstract void e(String paramString1, String paramString2);

  public abstract void e(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void e(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract void e(String paramString1, Throwable paramThrowable, String paramString2, Object[] paramArrayOfObject);

  public abstract void wtf(String paramString1, String paramString2);

  public abstract void wtf(String paramString1, String paramString2, Throwable paramThrowable);

  public abstract void wtf(String paramString, Throwable paramThrowable);

  public abstract void wtf(String paramString1, String paramString2, Object[] paramArrayOfObject);

  public abstract void wtf(String paramString1, Throwable paramThrowable, String paramString2, Object[] paramArrayOfObject);

  public abstract void trace(int paramInt, String paramString1, String paramString2, String paramString3);
}