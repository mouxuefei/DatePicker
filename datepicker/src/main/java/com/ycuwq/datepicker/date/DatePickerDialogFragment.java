package com.ycuwq.datepicker.date;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ycuwq.datepicker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatePickerDialogFragment extends DialogFragment {

    protected DatePicker mDatePickerStart;
    protected DatePicker mDatePickerEnd;
    private int mSelectedStartYear = -1, mSelectedStartMonth = -1, mSelectedStartDay = -1;
    private int mSelectedEndYear = -1, mSelectedEndMonth = -1, mSelectedEndDay = -1;
    private OnDateChooseListener mOnDateChooseListener;
    protected TextView mCancelButton, mDecideButton;

    public void setOnDateChooseListener(OnDateChooseListener onDateChooseListener) {
        mOnDateChooseListener = onDateChooseListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_date, container);
        initStart(view);
        initEnd(view);
        mCancelButton = view.findViewById(R.id.btn_dialog_date_cancel);
        mDecideButton = view.findViewById(R.id.btn_dialog_date_decide);
        mCancelButton.setOnClickListener(v -> dismiss());
        mDecideButton.setOnClickListener(v -> {
            if (mOnDateChooseListener != null) {
                mOnDateChooseListener.onStartDateChoose(mDatePickerStart.getYear(),
                        mDatePickerStart.getMonth(), mDatePickerStart.getDay());
                mOnDateChooseListener.onEndDateChoose(mDatePickerEnd.getYear(),
                        mDatePickerEnd.getMonth(), mDatePickerEnd.getDay());
            }
            dismiss();
        });

        if (mSelectedStartYear > 0) {
            setSelectedStartDate();
        }

        if (mSelectedEndYear > 0) {
            setSelectedEndDate();
        }

        initChild();
        return view;
    }

    private void initEnd(View view) {
        mDatePickerEnd = view.findViewById(R.id.dayPicker_dialogEnd);
        mDatePickerEnd.setMaxDate(System.currentTimeMillis());
        mDatePickerEnd.setDateViewVisible(true, true, true);
        mDatePickerEnd.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                mSelectedEndYear = year;
                mSelectedEndMonth = month;
                mSelectedEndDay = day;
                String str = "" + year + month + day;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                try {
                    long millionSeconds = sdf.parse(str).getTime();//毫秒
                    mDatePickerStart.setMaxDate(millionSeconds);
                } catch (ParseException e) {

                }
            }
        });
    }

    private void initStart(View view) {
        mDatePickerStart = view.findViewById(R.id.dayPicker_dialogStart);
        mDatePickerStart.setMaxDate(System.currentTimeMillis());
        mDatePickerStart.setDateViewVisible(true, true, true);
        mDatePickerStart.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                mSelectedStartYear = year;
                mSelectedStartMonth = month;
                mSelectedStartDay = day;
            }
        });
    }

    protected void initChild() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.DatePickerBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定

        dialog.setContentView(R.layout.dialog_date);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        Window window = dialog.getWindow();
        if (window != null) {

            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM; // 紧贴底部
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            lp.dimAmount = 0.35f;
            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        return dialog;
    }

    public void setSelectedStartDate(int year, int month, int day) {
        mSelectedStartYear = year;
        mSelectedStartMonth = month;
        mSelectedStartDay = day;
        setSelectedStartDate();
    }

    public void setSelectedEndDate(int year, int month, int day) {
        mSelectedEndYear = year;
        mSelectedEndMonth = month;
        mSelectedEndDay = day;
        setSelectedEndDate();
    }

    private void setSelectedStartDate() {
        if (mDatePickerStart != null) {
            mDatePickerStart.setDate(mSelectedStartYear, mSelectedStartMonth, mSelectedStartDay, false);
        }
    }

    private void setSelectedEndDate() {
        if (mDatePickerEnd != null) {
            mDatePickerEnd.setDate(mSelectedEndYear, mSelectedEndMonth, mSelectedEndDay, false);
        }
    }

    public interface OnDateChooseListener {
        void onStartDateChoose(int startYear, int startMonth, int startDay);

        void onEndDateChoose(int endYear, int endMonth, int endDay);
    }
}
