package crc6493855b22b6fa0721;


public class TextToSpeechInternalImplementation
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		android.speech.tts.TextToSpeech.OnInitListener,
		android.speech.tts.TextToSpeech.OnUtteranceCompletedListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onInit:(I)V:GetOnInit_IHandler:Android.Speech.Tts.TextToSpeech/IOnInitListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n" +
			"n_onUtteranceCompleted:(Ljava/lang/String;)V:GetOnUtteranceCompleted_Ljava_lang_String_Handler:Android.Speech.Tts.TextToSpeech/IOnUtteranceCompletedListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Media.TextToSpeechInternalImplementation, Microsoft.Maui.Essentials", TextToSpeechInternalImplementation.class, __md_methods);
	}


	public TextToSpeechInternalImplementation ()
	{
		super ();
		if (getClass () == TextToSpeechInternalImplementation.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Media.TextToSpeechInternalImplementation, Microsoft.Maui.Essentials", "", this, new java.lang.Object[] {  });
		}
	}


	public void onInit (int p0)
	{
		n_onInit (p0);
	}

	private native void n_onInit (int p0);


	public void onUtteranceCompleted (java.lang.String p0)
	{
		n_onUtteranceCompleted (p0);
	}

	private native void n_onUtteranceCompleted (java.lang.String p0);

	private java.util.ArrayList refList;
	public void monodroidAddReference (java.lang.Object obj)
	{
		if (refList == null)
			refList = new java.util.ArrayList ();
		refList.add (obj);
	}

	public void monodroidClearReferences ()
	{
		if (refList != null)
			refList.clear ();
	}
}
