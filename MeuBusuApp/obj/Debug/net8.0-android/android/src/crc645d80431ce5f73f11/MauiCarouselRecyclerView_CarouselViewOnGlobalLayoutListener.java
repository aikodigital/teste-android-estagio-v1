package crc645d80431ce5f73f11;


public class MauiCarouselRecyclerView_CarouselViewOnGlobalLayoutListener
	extends java.lang.Object
	implements
		mono.android.IGCUserPeer,
		android.view.ViewTreeObserver.OnGlobalLayoutListener
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_onGlobalLayout:()V:GetOnGlobalLayoutHandler:Android.Views.ViewTreeObserver/IOnGlobalLayoutListenerInvoker, Mono.Android, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Controls.Handlers.Items.MauiCarouselRecyclerView+CarouselViewOnGlobalLayoutListener, Microsoft.Maui.Controls", MauiCarouselRecyclerView_CarouselViewOnGlobalLayoutListener.class, __md_methods);
	}


	public MauiCarouselRecyclerView_CarouselViewOnGlobalLayoutListener ()
	{
		super ();
		if (getClass () == MauiCarouselRecyclerView_CarouselViewOnGlobalLayoutListener.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Items.MauiCarouselRecyclerView+CarouselViewOnGlobalLayoutListener, Microsoft.Maui.Controls", "", this, new java.lang.Object[] {  });
		}
	}

	public MauiCarouselRecyclerView_CarouselViewOnGlobalLayoutListener (crc645d80431ce5f73f11.MauiCarouselRecyclerView p0)
	{
		super ();
		if (getClass () == MauiCarouselRecyclerView_CarouselViewOnGlobalLayoutListener.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Handlers.Items.MauiCarouselRecyclerView+CarouselViewOnGlobalLayoutListener, Microsoft.Maui.Controls", "Microsoft.Maui.Controls.Handlers.Items.MauiCarouselRecyclerView, Microsoft.Maui.Controls", this, new java.lang.Object[] { p0 });
		}
	}


	public void onGlobalLayout ()
	{
		n_onGlobalLayout ();
	}

	private native void n_onGlobalLayout ();

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
