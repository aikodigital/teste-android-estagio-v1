package crc64e1fb321c08285b90;


public abstract class ViewRenderer
	extends crc64e1fb321c08285b90.ViewRenderer_2
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Controls.Handlers.Compatibility.ViewRenderer, Microsoft.Maui.Controls", ViewRenderer.class, __md_methods);
	}


	public ViewRenderer (android.content.Context p0, android.util.AttributeSet p1, int p2, int p3)
	{
		super (p0, p1, p2, p3);
		if (getClass () == ViewRenderer.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Compatibility.ViewRenderer, Microsoft.Maui.Controls", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, System.Private.CoreLib:System.Int32, System.Private.CoreLib", this, new java.lang.Object[] { p0, p1, p2, p3 });
		}
	}


	public ViewRenderer (android.content.Context p0, android.util.AttributeSet p1, int p2)
	{
		super (p0, p1, p2);
		if (getClass () == ViewRenderer.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Compatibility.ViewRenderer, Microsoft.Maui.Controls", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android:System.Int32, System.Private.CoreLib", this, new java.lang.Object[] { p0, p1, p2 });
		}
	}


	public ViewRenderer (android.content.Context p0, android.util.AttributeSet p1)
	{
		super (p0, p1);
		if (getClass () == ViewRenderer.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Compatibility.ViewRenderer, Microsoft.Maui.Controls", "Android.Content.Context, Mono.Android:Android.Util.IAttributeSet, Mono.Android", this, new java.lang.Object[] { p0, p1 });
		}
	}


	public ViewRenderer (android.content.Context p0)
	{
		super (p0);
		if (getClass () == ViewRenderer.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Compatibility.ViewRenderer, Microsoft.Maui.Controls", "Android.Content.Context, Mono.Android", this, new java.lang.Object[] { p0 });
		}
	}

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
