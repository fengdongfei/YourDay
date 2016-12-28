package com.wuxiao.yourday.note;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wuxiao.yourday.R;
import com.wuxiao.yourday.base.BaseFragment;
import com.wuxiao.yourday.bean.Note;
import com.wuxiao.yourday.common.ThemeManager;
import com.wuxiao.yourday.databinding.FragmentNoteBinding;
import com.wuxiao.yourday.home.HomeActivity;
import com.wuxiao.yourday.setting.SettingActivity;
import com.wuxiao.yourday.viewpager.FragmentVisibilityListener;

import java.util.List;

/**
 * Created by wuxiaojian on 16/12/4.
 */
public class NoteFragment extends BaseFragment<NotePresenter> implements NoteContract.View, FragmentVisibilityListener, View.OnClickListener {



    private RecyclerView note_list;
    private LinearLayout buttom_toolbar;
    private ImageView compile;
    private ImageView set;
    private NoteAdapter noteAdapter;
    private FragmentNoteBinding fragmentNoteBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentNoteBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);
        note_list = fragmentNoteBinding.noteList;
        fragmentNoteBinding.setSetThemeBg(ThemeManager.getInstance().getBgDrawable(getActivity()));
        fragmentNoteBinding.setSetTextColor(ThemeManager.getInstance().getThemeColor(getActivity()));
        note_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        compile = (ImageView) fragmentNoteBinding.getRoot().findViewById(R.id.compile);
        compile.setVisibility(View.VISIBLE);
        compile.setOnClickListener(this);
        set = (ImageView) fragmentNoteBinding.getRoot().findViewById(R.id.set);
        set.setVisibility(View.VISIBLE);
        set.setOnClickListener(this);
        buttom_toolbar = (LinearLayout) fragmentNoteBinding.getRoot().findViewById(R.id.buttom_toolbar);
        buttom_toolbar.setBackgroundColor(ThemeManager.getInstance().getThemeColor(getActivity()));
        mPresenter.getNoteList();
        return fragmentNoteBinding.getRoot();
    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }


    @Override
    public void onFragmentVisible() {
        if (NoteAdapter.clickItem) {
            mPresenter.getNoteList();
            NoteAdapter.clickItem = false;
        }
    }

    @Override
    public void onFragmentInvisible() {

    }


    @Override
    protected NotePresenter getPresenter() {
        return new NotePresenter(getActivity(), this);
    }

    @Override
    public void loadView(Throwable e) {

    }


    @Override
    public void responseNoteList(List<Note> noteList) {
        noteAdapter = new NoteAdapter(noteList, getActivity());
        note_list.setAdapter(noteAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compile:
                HomeActivity home = (HomeActivity) getActivity();
                home.setHomeCurrentItem(2);
                break;
            case R.id.set:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }


    }
}
