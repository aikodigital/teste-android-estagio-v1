package crc6452ffdc5b34af3a0f;


public class ContentViewGroup
	extends com.microsoft.maui.PlatformContentViewGroup
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onMeasure:(II)V:GetOnMeasure_IIHandler\n" +
			"n_onLayout:(ZIIII)V:GetOnLayout_ZIIIIHandler\n" +
			"n_getClipPath:(II)Landroid/graphics/Path;:GetGetClipPath_IIHandler\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Platform.ContentViewGroup, Microsoft.Maui", ContentViewGroup.class, __md_methods);
	}


	public ContentViewGroup (android.content.Context p0)
	{
		super (p0);
		if (getClass () == ContentViewGroup.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Platform.ContentViewGroup, Microsoft.Maui", "Android.Content.Context, Mono.Android", this, new java.lang.Object[] { p0 });
		}
	}


	public ContentViewGroup (android.content.Context p0, android.util.AttributeSet p1)
	{
		super (p0, p1);
		if (getClass () == ContentViewGroup.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Platform.ContentViewGroup, Microsoft.Maui", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new java.lang.Object[] { p0, p1 });
		}
	}


	public ContentViewGroup (android.content.Context p0, android.util.AttributeSet p1, int p2)
	{
		super (p0, p1, p2);
		if (getClass () == ContentViewGroup.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Platform.ContentViewGroup, Microsoft.Maui", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, System.Private.CoreLib", this, new java.lang.Object[] { p0, p1, p2 });
		}
	}


	public ContentViewGroup (android.content.Context p0, android.util.AttributeSet p1, int p2, int p3)
	{
		super (p0, p1, p2, p3);
		if (getClass () == ContentViewGroup.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Platform.ContentViewGroup, Microsoft.Maui", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, System.Private.CoreLib:System.Int32, System.Private.CoreLib", this, new java.lang.Object[] { p0, p1, p2, p3 });
		}
	}


	public void onMeasure (int p0, int p1)
	{
		n_onMeasure (p0, p1);
	}

	private native void n_onMeasure (int p0, int p1);


	public void onLayout (boolean p0, int p1, int p2, int p3, int p4)
	{
		n_onLayout (p0, p1, p2, p3, p4);
	}

	private native void n_onLayout (boolean p0, int p1, int p2, int p3, int p4);


	public android.graphics.Path getClipPath (int p0, int p1)
	{
		return n_getClipPath (p0, p1);
	}

	private native android.graphics.Path n_getClipPath (int p0, int p1);

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
