package crc6477f0d89a9cfd64b1;


public abstract class NongreedySnapHelper
	extends androidx.recyclerview.widget.LinearSnapHelper
	implements
		mono.android.IGCUserPeer
{
/** @hide */
	public static final String __md_methods;
	static {
		__md_methods = 
			"n_attachToRecyclerView:(Landroidx/recyclerview/widget/RecyclerView;)V:GetAttachToRecyclerView_Landroidx_recyclerview_widget_RecyclerView_Handler\n" +
			"";
		mono.android.Runtime.register ("Microsoft.Maui.Controls.Compatibility.Platform.Android.NongreedySnapHelper, Microsoft.Maui.Controls.Compatibility", NongreedySnapHelper.class, __md_methods);
	}


	public NongreedySnapHelper ()
	{
		super ();
		if (getClass () == NongreedySnapHelper.class) {
			mono.android.TypeManager.Activate ("Microsoft.Maui.Controls.Compatibility.Platform.Android.NongreedySnapHelper, Microsoft.Maui.Controls.Compatibility", "", this, new java.lang.Object[] {  });
		}
	}


	public void attachToRecyclerView (androidx.recyclerview.widget.RecyclerView p0)
	{
		n_attachToRecyclerView (p0);
	}

	private native void n_attachToRecyclerView (androidx.recyclerview.widget.RecyclerView p0);

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
