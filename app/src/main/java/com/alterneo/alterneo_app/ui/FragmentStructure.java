package com.alterneo.alterneo_app.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.alterneo.alterneo_app.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class FragmentStructure<T extends ViewBinding> extends Fragment {

	protected MainActivity mainActivity;
	protected T binding;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);

		if (context instanceof MainActivity) {
			mainActivity = (MainActivity) context;
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		try {
			Method inflate = getClassBinding().getMethod("inflate", LayoutInflater.class);
			binding = (T) inflate.invoke(null, inflater);

		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new NullPointerException("Bruh");
		}
		return binding.getRoot();
	}

	protected abstract Class<T> getClassBinding();

	protected abstract void viewCreated();
}
