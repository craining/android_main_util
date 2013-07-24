package com.zgy.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Contacts.ContactMethods;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Intents;

import com.zgy.mainutil.R;
import com.zgy.util.beans.ContactBean;

public class ContactsUtil {

	private static final String TAG = "ContactsUtil";

	/**
	 * 根据号码获得联系人姓名
	 * 
	 * @Description:
	 * @param con
	 * @param number
	 * @return
	 * @see:
	 * @since:
	 * @author: zgy
	 * @date:2012-8-29
	 */
	public static String getNameFromContactsByNumber(Context con, String number) {
		String name = number;

		// 从手机通讯录查找
		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
		Cursor cursor = null;
		try {
			cursor = con.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				String newNumber = "";
				do {
					newNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					newNumber = StringUtil.getRidofSpeciallOfTel(newNumber);
					if (newNumber.contains(number) || number.contains(newNumber)) {
						name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
						break;
					}
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}

		}

		// 未获取到姓名，尝试从sim卡里获取
		if (name.equals(number)) {
			Cursor cur = null;
			try {
				cur = con.getContentResolver().query(Uri.parse("content://icc/adn"), null, null, null, null);
				if (cur != null && cur.getCount() > 0) {
					cur.moveToFirst();
					String num = "";
					do {
						num = cur.getString(cur.getColumnIndex(People.NUMBER));
						num = StringUtil.getRidofSpeciallOfTel(num);
						if (num.contains(number) || number.contains(num)) {
							name = cur.getString(cur.getColumnIndex(People.NAME)) + ":" + num;
							break;
						}

					} while (cur.moveToNext());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cur != null) {
					cur.close();
				}
			}
		}

		return name;
	}

	/**
	 * 从手机通讯录里获得联系人
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-5-31
	 */
	public static ArrayList<ContactBean> getAllContactsFromLocal(Context con) {
		ArrayList<ContactBean> arrayContacts = new ArrayList<ContactBean>();
		ContactBean c;

		// 从手机通讯录里查找
		Cursor cursor = null;
		try {
			String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
			cursor = con.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					c = new ContactBean();
					c.name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
					c.number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					if (!StringUtil.isNull(c.number)) {
						c.number = StringUtil.getRidofSpeciallOfTel(c.number);
					}
					arrayContacts.add(c);

				} while (cursor.moveToNext());

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return arrayContacts;
	}

	/**
	 * 从sim卡里获取联系人
	 * 
	 * @Description:
	 * @param con
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-8
	 */
	public static ArrayList<ContactBean> getAllContactsFromSim(Context con) {
		ArrayList<ContactBean> arrayContacts = new ArrayList<ContactBean>();
		ContactBean c;

		// 从sim卡里查找
		Cursor cur = null;
		try {
			cur = con.getContentResolver().query(Uri.parse("content://icc/adn"), null, null, null, null);
			if (cur != null && cur.getCount() > 0) {
				cur.moveToFirst();
				String num = "";
				do {
					c = new ContactBean();
					c.name = cur.getString(cur.getColumnIndex(People.NAME));
					c.number = cur.getString(cur.getColumnIndex(People.NUMBER));
					if (!StringUtil.isNull(c.number)) {
						c.number = StringUtil.getRidofSpeciallOfTel(c.number);
					}
					arrayContacts.add(c);
				} while (cur.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cur != null) {
				cur.close();
			}
		}
		return arrayContacts;
	}

	/**
	 * 根据联系人id或电话号码获得姓名
	 * 
	 * @Description:
	 * @param con
	 * @param id
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-8
	 */
	public static String getContactNameById(Context con, int id, String number) {
		String result = number;
		Debug.e(TAG, "id=" + id);
		if (id > 0) {
			// 手机通讯录里有
			String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME };
			Cursor cursor = null;
			try {
				cursor = con.getContentResolver().query(ContactsContract.RawContacts.CONTENT_URI, projection, ContactsContract.PhoneLookup._ID + "=?", new String[] { id + "" }, null);
				if (cursor != null && cursor.getCount() > 0) {
					cursor.moveToFirst();
					result = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
					Debug.e(TAG, "result=" + result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}

			}
		} else {
			// 尝试从sim卡里取
			result = getNameFromContactsByNumber(con, number);
		}

		return result;

	}

	/**
	 * 生成通讯录文件
	 * 
	 * @Description:
	 * @param con
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-7-8
	 */
	public static void createContactsFile(Context con, File outputFile) {

		boolean isNull = true;
		ArrayList<ContactBean> contacts = ContactsUtil.getAllContactsFromLocal(con);
		if (contacts != null && contacts.size() > 0) {
			isNull = false;
			String contactsStr = "手机通讯录：\r\n\r\n";
			for (ContactBean c : contacts) {
				contactsStr = contactsStr + "电话：" + c.number + "    姓名：" + c.name + "\r\n";
			}
			if (!contactsStr.equals("") && contactsStr.length() > 0) {
				FileUtil.writeFile(contactsStr, outputFile, false);
			}
		}

		contacts = new ArrayList<ContactBean>();
		contacts = ContactsUtil.getAllContactsFromSim(con);
		if (contacts != null && contacts.size() > 0) {
			isNull = false;
			String contactsStr = "\r\n\r\n\r\nSIM卡通信录：\r\n\r\n";
			for (ContactBean c : contacts) {
				contactsStr = contactsStr + "电话：" + c.number + "    姓名：" + c.name + "\r\n";
			}
			if (!contactsStr.equals("") && contactsStr.length() > 0) {
				FileUtil.writeFile(contactsStr, outputFile, true);
			}
		}

		if (isNull) {
			FileUtil.writeFile("通讯录为空！", outputFile, true);
		}

	}

	/******************************************* 与联系人中的email地址相关的一些操作  ******************************************************/

	/**
	 * 通过邮件地址获取 联系人姓名·
	 * 
	 * @param mailAddress
	 * @param context
	 * @return
	 */
	public static String getNameByMailAddress(String mailAddress, Context context) {
		if (mailAddress != null && !"".equals(mailAddress)) {
			final String[] ContactMethods_NAME = new String[] { ContactMethods.NAME };
			ContentResolver oResolver = context.getContentResolver();
			Cursor cCursor = oResolver.query(ContactMethods.CONTENT_URI, ContactMethods_NAME, ContactMethods.DATA + "=?", new String[] { mailAddress.trim() }, null);
			if (cCursor.getCount() > 0) {
				try {
					while (cCursor.moveToNext()) {
						if (cCursor.getColumnName(cCursor.getColumnIndex("name")) != null) {
							return cCursor.getString(cCursor.getColumnIndex("name"));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Debug.e("failfast", "failfast_AA", e);
				} finally {
					cCursor.close();
				}
			}

		}
		return null;

	}

	/**
	 * 通过邮件地址 获得 联系人 图片
	 * 
	 * @param mailAddress
	 * @return
	 */
	public static Bitmap getImageByMailAddress(String mailAddress, Context context) {
		if (mailAddress != null && !"".equals(mailAddress)) {
			ContentResolver contentResolver = context.getContentResolver();
			String[] selectionArgs = new String[] { Email.CONTENT_ITEM_TYPE, mailAddress };
			Cursor cursor = contentResolver.query(Data.CONTENT_URI, new String[] { Email.RAW_CONTACT_ID }, Data.MIMETYPE + "=? AND " + Email.DATA1 + "=?", selectionArgs, null);
			if (cursor != null) {
				try {
					while (cursor.moveToNext()) {
						int rawcontactId = cursor.getInt(cursor.getColumnIndex(Email.RAW_CONTACT_ID));
						Cursor c = contentResolver.query(Data.CONTENT_URI, new String[] { Photo.PHOTO }, Data.MIMETYPE + "='" + Photo.CONTENT_ITEM_TYPE + "' AND " + Photo.RAW_CONTACT_ID + "=" + rawcontactId, null, null);
						if (c != null) {
							try {
								while (c.moveToNext()) {
									byte[] b = c.getBlob(c.getColumnIndex(Photo.PHOTO));
									if (null != b) {
										return BitmapFactory.decodeByteArray(b, 0, b.length);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								Debug.e("failfast", "failfast_AA", e);
							} finally {
								c.close();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Debug.e("failfast", "failfast_AA", e);
				} finally {
					cursor.close();
				}
			}
		}
		return null;
	}

	/**
	 * 判断通讯录中是否存在该联系人，用mail检索
	 * 
	 * @param emailAdderss
	 * @return
	 */
	public static Boolean contactIsExist(Context activity, String emailAdderss) {
		if (emailAdderss != null) {
			ContentResolver contentResolver = activity.getContentResolver();
			String[] selectionArgs = new String[] { Email.CONTENT_ITEM_TYPE, emailAdderss };
			Cursor cursor = contentResolver.query(Data.CONTENT_URI, new String[] { Email.RAW_CONTACT_ID }, Data.MIMETYPE + "=? AND " + Email.DATA1 + "=?", selectionArgs, null);
			int n = cursor.getCount();
			cursor.close();
			if (n > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据邮箱地址获取手机号码
	 * 
	 * @param mailAdderss
	 * @return
	 */
	public static List<String> getTelNumByMail(Context activity, String emailAdderss) {
		if (contactIsExist(activity, emailAdderss)) {
			ContentResolver contentResolver = activity.getContentResolver();
			String[] selectionArgs = new String[] { Email.CONTENT_ITEM_TYPE, emailAdderss };
			Cursor cursor = contentResolver.query(Data.CONTENT_URI, new String[] { Email.RAW_CONTACT_ID }, Data.MIMETYPE + "=? AND " + Email.DATA1 + "=?", selectionArgs, null);
			ArrayList<String> phoneList = new ArrayList<String>();
			if (cursor != null) {
				while (cursor.moveToNext()) {
					int rawcontactId = cursor.getInt(cursor.getColumnIndex(Email.RAW_CONTACT_ID));
					Cursor c = contentResolver.query(Data.CONTENT_URI, new String[] { Phone.NUMBER }, Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "' AND " + Phone.RAW_CONTACT_ID + "=" + rawcontactId, null, null);
					if (c != null) {
						while (c.moveToNext()) {
							String number = c.getString(c.getColumnIndex(Phone.NUMBER));
							phoneList.add(number);
						}
						c.close();
					}
				}
				cursor.close();
			}
			return phoneList;
		} else {
			return null;
		}

	}

	/**
	 * 编辑当前用户的联系人操作
	 * 
	 * @param activity
	 * @param emailAdderss
	 */
	public static void editContact(Activity activity, String emailAdderss) {
		Integer id = null;
		ContentResolver contentResolver = activity.getContentResolver();
		String[] selectionArgs = new String[] { Email.CONTENT_ITEM_TYPE, emailAdderss };
		Cursor cursor = contentResolver.query(Data.CONTENT_URI, new String[] { Email.RAW_CONTACT_ID }, Data.MIMETYPE + "=? AND " + Email.DATA1 + "=?", selectionArgs, null);
		cursor.moveToFirst();
		id = cursor.getInt(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
		cursor.close();
		try {
			if (Build.VERSION.SDK_INT == 4 || Build.VERSION.RELEASE.equals("1.6")) {
				// for sdk1.6
				Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/" + id));
				activity.startActivity(intent);
			} else {
				// for sdk2.1
				Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://com.android.contacts/raw_contacts/" + id));
				activity.startActivity(intent);
			}
		} catch (Exception e) {
			Debug.e("failfast", "failfast_AA", e);
		}
	}

	/**
	 * 添加当前用户为本机联系人
	 * 
	 * @param activity
	 * @param emailAdderss
	 * @param name
	 * @param phone
	 * @param curContact
	 */
	public static void addContact(Activity activity, String emailAdderss, String name) {
		try {
			Intent intent = new Intent(Intent.ACTION_INSERT, People.CONTENT_URI);
			if (emailAdderss != null) {
				intent.putExtra(Intents.Insert.EMAIL, emailAdderss);
				intent.putExtra(Intents.Insert.NAME, name);
			}
			activity.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			Debug.e("failfast", "failfast_AA", e);
		}
	}
}
