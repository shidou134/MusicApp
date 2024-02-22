package com.example.musicapp.data

import androidx.compose.ui.res.stringResource
import com.example.musicapp.R
import com.example.musicapp.model.Song

object DataSource {
    val songs = listOf(
        Song(
            artist = R.string.thanh_thao,
            name = R.string.co_quen_duoc_dau,
            image = R.drawable.co_quen_duoc_dau,
            duration = "4:14",
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FCoQuenDuocDau-ThanhThao-8969228.mp3?alt=media&token=f7acdfb9-d0eb-4703-ac69-86719e76cec9"
        ),
        Song(
            artist = R.string.tran_thu_ha,
            name = R.string.em_ve_tinh_khoi,
            image = R.drawable.em_ve_tinh_khoi,
            duration = "4:47",
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FEmVeTinhKhoi-TranThuHa-6853100.mp3?alt=media&token=e6278689-b98f-4e17-8e12-063cfb2bddc0"
        ),
        Song(
            artist = R.string.minh_thuan,
            name = R.string.noi_dau_ngot_ngao,
            image = R.drawable.noi_dau_ngot_ngao,
            duration = "4:45",
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FNoiDauNgotNgao-MinhThuan-2615659.mp3?alt=media&token=993b2365-3267-44bc-9eab-9525afdb7e85"
        ),
        Song(
            artist = R.string.phuong_thanh,
            name = R.string.tinh_co,
            image = R.drawable.tinh_co,
            duration = "3:50",
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FTinhCo-PhuongThanh_b3j.mp3?alt=media&token=e1667491-d1df-4531-81b2-82e6179b2399"
        ),
        Song(
            artist = R.string.tuan_hung,
            name = R.string.tinh_yeu_lung_linh,
            image = R.drawable.tinh_yeu_lung_linh,
            duration = "4:14",
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FTinhYeuLungLinh-TuanHung_bk.mp3?alt=media&token=d8acefef-f6b9-4e92-a3a2-4b7fcf8805f1"
        ),
        Song(
            artist = R.string.ho_quynh_huong,
            name = R.string.uoc_mo_trong_doi,
            image = R.drawable.uoc_mo_trong_doi,
            duration = "3:26",
            url = "https://firebasestorage.googleapis.com/v0/b/music-app-2524d.appspot.com/o/music%2FUocMoTrongDoi-HoQuynhHuong_443ta.mp3?alt=media&token=c1ed9955-d91b-40a4-90cf-8084d4f6d6fe"
        )
    )
}