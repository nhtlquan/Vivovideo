#include <jni.h>
#include <android/log.h>
#include <string>
#include <sstream>
#include <iostream>

//declare methods
void LOGD_TEST(char *, jint);

jboolean compareString(JNIEnv *, jstring, jstring);

const char HexCode[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
                        'F'};

extern "C"
jstring


Java_com_dolphin_DataVerifier_verifyData(
        JNIEnv *env,
        jobject /* this */, jobject activity, jstring input) {
    try {
        //get package name and app name
        jclass android_content_Context = env->GetObjectClass(activity);

        jmethodID midGetPackageName = env->GetMethodID(android_content_Context,
                                                       "getPackageName", "()Ljava/lang/String;");
        jstring packageName = (jstring) env->CallObjectMethod(activity, midGetPackageName);
//        __android_log_write(ANDROID_LOG_ERROR, "test", env->GetStringUTFChars(packageName, 0));

        jmethodID midGetPackageManager = env->GetMethodID(android_content_Context,
                                                          "getPackageManager",
                                                          "()Landroid/content/pm/PackageManager;");
        jobject pkm = env->CallObjectMethod(activity, midGetPackageManager);
        jclass android_content_pm_PackageManager = env->GetObjectClass(pkm);

        //for temporary, because cannot get constant field GET_META_DATA of class android/content/pm/PackageManager
//        jclass com_constants = env->FindClass("com/constants/Constants");
//        jfieldID fidGET_META_DATA = env->GetStaticFieldID(com_constants, "GET_META_DATA",
//                                                          "I");
//        jint GET_META_DATA = env->GetStaticIntField(com_constants, fidGET_META_DATA);
//        __android_log_write(ANDROID_LOG_ERROR, "test", "GET_META_DATA");

        jmethodID midGetApplicationInfo = env->GetMethodID(android_content_pm_PackageManager,
                                                           "getApplicationInfo",
                                                           "(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;");
        jobject appInfo = env->CallObjectMethod(pkm, midGetApplicationInfo, packageName,
                                                128);

        jmethodID midGetApplicationLabel = env->GetMethodID(android_content_pm_PackageManager,
                                                            "getApplicationLabel",
                                                            "(Landroid/content/pm/ApplicationInfo;)Ljava/lang/CharSequence;");
        jobject objChar = (jstring) env->CallObjectMethod(pkm, midGetApplicationLabel, appInfo);
        jclass java_lang_CharSequence = env->GetObjectClass(objChar);
        jmethodID midToString = env->GetMethodID(java_lang_CharSequence,
                                                 "toString", "()Ljava/lang/String;");
        jstring appName = (jstring) env->CallObjectMethod(objChar, midToString);

        //get app signature
        jmethodID midGetPackageInfo = env->GetMethodID(android_content_pm_PackageManager,
                                                       "getPackageInfo",
                                                       "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
        jobject pkgInfo = env->CallObjectMethod(pkm, midGetPackageInfo, packageName, 64);
        jclass android_content_pm_PackageInfo = env->GetObjectClass(pkgInfo);
        jfieldID fidSignatures = env->GetFieldID(android_content_pm_PackageInfo, "signatures",
                                                 "[Landroid/content/pm/Signature;");

        jobjectArray signatures = (jobjectArray) env->GetObjectField(pkgInfo, fidSignatures);
        jsize size = env->GetArrayLength(signatures);
//    LOGD_TEST("signatures ==== ", size);
        std::string signatureDiggest;
        for (int i = 0; i < size; i++) {
            jobject signature = env->GetObjectArrayElement(signatures, i);
            jclass android_content_pm_Signature = env->GetObjectClass(signature);
            jmethodID midToByteArray = env->GetMethodID(android_content_pm_Signature,
                                                        "toByteArray", "()[B");
            jbyteArray byteArr = (jbyteArray) env->CallObjectMethod(signature, midToByteArray);
            jclass java_security_MessageDigest = env->FindClass("java/security/MessageDigest");
            jmethodID midGetInstance = env->GetStaticMethodID(java_security_MessageDigest,
                                                              "getInstance",
                                                              "(Ljava/lang/String;)Ljava/security/MessageDigest;");
            jstring SHA1 = env->NewStringUTF("SHA1");
            jobject msgDigest = env->CallStaticObjectMethod(java_security_MessageDigest,
                                                            midGetInstance,
                                                            SHA1);
            jmethodID midUpdate = env->GetMethodID(java_security_MessageDigest,
                                                   "update", "([B)V");
            env->CallVoidMethod(msgDigest, midUpdate, byteArr);
            jmethodID midDigest = env->GetMethodID(java_security_MessageDigest,
                                                   "digest", "()[B");
            jbyteArray byteArrDigest = (jbyteArray) env->CallObjectMethod(msgDigest, midDigest);

            int len = env->GetArrayLength(byteArrDigest);
            unsigned char *bufCharDig = new unsigned char[len];
            env->GetByteArrayRegion(byteArrDigest, 0, len, reinterpret_cast<jbyte *>(bufCharDig));

            std::stringstream ss;

            for (int i = 0; i < len; i++) {
                signatureDiggest[i * 2] = HexCode[((unsigned char) bufCharDig[i]) / 16];
                signatureDiggest[i * 2 + 1] = HexCode[((unsigned char) bufCharDig[i]) % 16];
            }
            signatureDiggest[len * 2] = '\0';
        }

        jstring appSignature = env->NewStringUTF(signatureDiggest.c_str());

//        __android_log_write(ANDROID_LOG_ERROR, "test", env->GetStringUTFChars(appSignature, 0));

        //execute decrypt
        if (compareString(env, packageName, env->NewStringUTF("vn.lequan.vivovideo")) ==
            JNI_TRUE
            && compareString(env, appName, env->NewStringUTF("VTVPlay")) == JNI_TRUE
            && compareString(env, appSignature,
                             env->NewStringUTF("2E36C6208075D3C78FAB36B85D347508CBB63888")) ==
//                              env->NewStringUTF("E74A11F17C539930A3AF6C69FBF75481AF8DAAE2")) ==
               JNI_TRUE) {

//            __android_log_write(ANDROID_LOG_ERROR, "test", "FUCKKKKKKKK!!!!!!!!!!!!!!!!!!");
            //decode base 64
            jclass android_util_Base64 = env->FindClass("android/util/Base64");
            jfieldID fidDEFAULT = env->GetStaticFieldID(android_util_Base64, "DEFAULT",
                                                        "I");
            jint DEFAULT = env->GetStaticIntField(android_util_Base64, fidDEFAULT);
            jmethodID midDecode = env->GetStaticMethodID(android_util_Base64,
                                                         "decode",
                                                         "(Ljava/lang/String;I)[B");
//        jstring input = env->NewStringUTF("NwM6DhANqFqxNzJlzfvLWTheyuav9Ux8NOW8fsKbRavXxmFqQJKln8jDf85TlAnDEEOqjRSwAYZm2TEOr9jK2jtyu4zM32LMBJF181fU99mJRUp3LDM35BQAjBZYS/BdM9Jcb146+aeT88Pt4U0TckLnMf89L253ZsE/U4GKttF1D57XWCuBjSV/OjxVdZyHD/RyvZraN5OrP9N+DNG3JUGPdgDVgeWw1FdHxKHhVaD/AZ8Nk0NfSIUg2xwJgh91ZrYcGvMRs6OcU68m74sdCTibi+HCTTlwTKQz8x/4md0=");
            jobjectArray decodedBytes = (jobjectArray) env->CallStaticObjectMethod(
                    android_util_Base64, midDecode,
                    input, DEFAULT);
            //copy to IV
            jclass java_util_Arrays = env->FindClass("java/util/Arrays");
            jmethodID midCopyOfRange = env->GetStaticMethodID(java_util_Arrays,
                                                              "copyOfRange",
                                                              "([BII)[B");
            jobjectArray IVBytes = (jobjectArray) env->CallStaticObjectMethod(java_util_Arrays,
                                                                              midCopyOfRange,
                                                                              decodedBytes, 0, 16);
            jsize decodedLen = env->GetArrayLength(decodedBytes);
            //real encrypted bytes
            jobjectArray encryptedBytes = (jobjectArray) env->CallStaticObjectMethod(
                    java_util_Arrays, midCopyOfRange,
                    decodedBytes, 16, decodedLen);

            jclass javax_crypto_Cipher = env->FindClass("javax/crypto/Cipher");
            jmethodID midGetInstance = env->GetStaticMethodID(javax_crypto_Cipher,
                                                              "getInstance",
                                                              "(Ljava/lang/String;)Ljavax/crypto/Cipher;");
            jstring AES_MODE = env->NewStringUTF("AES/CBC/PKCS7Padding");
            jobject cipher = env->CallStaticObjectMethod(javax_crypto_Cipher, midGetInstance,
                                                         AES_MODE);
            jfieldID fidDECRYPT_MODE = env->GetStaticFieldID(javax_crypto_Cipher, "DECRYPT_MODE",
                                                             "I");
            jint DECRYPT_MODE = env->GetStaticIntField(javax_crypto_Cipher, fidDECRYPT_MODE);

            jstring key = env->NewStringUTF("502922E03EF875203A15B47E295FF5EC");
            jclass java_lang_String = env->FindClass("java/lang/String");
            jmethodID midGetBytes = env->GetMethodID(java_lang_String, "getBytes",
                                                     "(Ljava/lang/String;)[B");
            jstring strencode = env->NewStringUTF("utf-8");
            jbyteArray barr = (jbyteArray) env->CallObjectMethod(key, midGetBytes, strencode);
            jsize alen = env->GetArrayLength(barr);
//        LOGD_TEST("KEY-LENGTH======== ",alen);

            jclass javax_crypto_spec_SecretKeySpec = env->FindClass(
                    "javax/crypto/spec/SecretKeySpec");
            jmethodID midConstructorKey = env->GetMethodID(javax_crypto_spec_SecretKeySpec,
                                                           "<init>", "([BLjava/lang/String;)V");

            jobject objKey = env->NewObject(javax_crypto_spec_SecretKeySpec, midConstructorKey,
                                            barr, env->NewStringUTF("AES"));


            jclass javax_crypto_spec_IvParameterSpec = env->FindClass(
                    "javax/crypto/spec/IvParameterSpec");
            jmethodID midConstructorIv = env->GetMethodID(javax_crypto_spec_IvParameterSpec,
                                                          "<init>", "([B)V");
            jobject objIv = env->NewObject(javax_crypto_spec_IvParameterSpec, midConstructorIv,
                                           IVBytes);

            jmethodID midInit = env->GetMethodID(javax_crypto_Cipher, "init",
                                                 "(ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V");
            env->CallVoidMethod(cipher, midInit, DECRYPT_MODE, objKey, objIv);

            jmethodID midDoFinal = env->GetMethodID(javax_crypto_Cipher, "doFinal", "([B)[B");
            jbyteArray decryptedBytes = (jbyteArray) env->CallObjectMethod(cipher, midDoFinal,
                                                                           encryptedBytes);

            jmethodID midConstructorString = env->GetMethodID(java_lang_String, "<init>", "([B)V");
            jstring result = (jstring) env->NewObject(java_lang_String, midConstructorString,
                                                      decryptedBytes);
            return result;
        }

        return env->NewStringUTF("");

    }
    catch (char *exception) {
        printf("An exception happened!!!");
        //Handle the exception.
    }

    return env->NewStringUTF("");
}

//print a message which contains number
void LOGD_TEST(char *prefix, jint num) {
    char bufDig[64];
    sprintf(bufDig, "%s%d", prefix, num);
    __android_log_write(ANDROID_LOG_DEBUG, "test", bufDig);
}

jboolean compareString(JNIEnv *env, jstring str1, jstring str2) {
    jclass cls = env->GetObjectClass(str1);
    jmethodID mID = env->GetMethodID(cls, "equals", "(Ljava/lang/Object;)Z");
    jboolean equals = env->CallBooleanMethod(str1, mID, str2);
    return equals;
}





