package com.example.test;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.model.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSong,txtTimeTotal;
    SeekBar skSong;
    ImageView imgHinh;
    ImageButton btnNext,btnBack,btnPlay,btnStop;


    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AnhXa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this,R.anim.disc_rotate);

        KhoiTaoMediaPlayer();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if (position > arraySong.size() - 1){
                    position = 0 ;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();

                    KhoiTaoMediaPlayer();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_button);
                    SetTimeTotal();
                    UpdateTimeSong();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if (position < 0){
                    position = arraySong.size() -1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                SetTimeTotal();
                UpdateTimeSong();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play_arrow);
                KhoiTaoMediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    // nếu đang phát -> pause -> đổi hình play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play_arrow);
                }else {
                    // đang ngừng phát -> đổi hình pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_button);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imgHinh.startAnimation(animation);
            }
        });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                skSong.setProgress(mediaPlayer.getCurrentPosition());

                // kiem tra thoi gian bai hat neu ket thuc thi next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if (position > arraySong.size() - 1){
                            position = 0 ;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();

                            KhoiTaoMediaPlayer();
                            mediaPlayer.start();
                            btnPlay.setImageResource(R.drawable.pause_button);
                            SetTimeTotal();
                            UpdateTimeSong();
                        }

                    }
                });
                handler.postDelayed(this, 500);
           }
        }, 100);
    }

    private void SetTimeTotal(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");

        txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getDuration())+ "");

        // gán max cảu skSong = mediaPlayer.getDuration()
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Chúng ta không thuộc về nhau",R.raw.chung_ta_khong_thuoc_ve_nhau));
        arraySong.add(new Song("Cơn mưa ngang qua",R.raw.con_mua_ngang_qua));
        arraySong.add(new Song("Cô thắm không về",R.raw.cotham_khongve));
        arraySong.add(new Song("Em của ngày hôm qua",R.raw.em_cua_ngay_hom_qua));
        arraySong.add(new Song("Mãi mãi bên nhau",R.raw.mai_mai_ben_nhau));
        arraySong.add(new Song("Nắng ấm xa dần",R.raw.nang_am_xa_dan));
        arraySong.add(new Song("Như ngày hôm qua",R.raw.nhu_ngay_hom_qua));


    }
    private void AnhXa(){
        txtTimeSong = (TextView) findViewById(R.id.tv1);
        txtTimeTotal = (TextView)findViewById(R.id.tv2);
        txtTitle = (TextView) findViewById(R.id.tvTenBaiHat);
        skSong = (SeekBar) findViewById(R.id.seeBarSong);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        imgHinh = (ImageView) findViewById(R.id.imageView);

    }

    public void tvYeuThich(View view) {
        Intent intent = new Intent(MainActivity.this,YeuThichActivity.class);
        startActivity(intent);
    }

    public void tvCaSy(View view) {
        Intent intent = new Intent(MainActivity.this,CaSyActivity.class);
        startActivity(intent);
    }
}
