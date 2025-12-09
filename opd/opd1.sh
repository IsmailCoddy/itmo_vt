#!/bin/bash
# часть 1
# создаю дерево каталогов
rm -rf lab0/
mkdir -p lab0
mkdir -p lab0/{ninjask4,pidgey6/{gorebyss,sceptile},vullaby2/{sharpedo,munchlax}}

# перехожу в директорию lab0 для простоты
cd lab0

# записываю содержимое в файлы дерева
touch ninjask4/ralts
echo "weight=14.6 height=16.0 atk=3 def=3" > ninjask4/ralts

cat <<EOF > ninjask4/probopass
Живет
Mountain
EOF

echo "satk=8 sdef=8 spd=11" > ninjask4/rapidash
echo -e "Ходы Earth Power\nFire Fang‡ Ice Fang‡ Iron Head Iron Tail Mud-Slap Sleep Talk Snore\nStealth Rock Superpower Thunder Fang‡ Water Pulse" > pidgey6/hippowdon
echo -e "Способности\nPure Blooded Landslide Sand Veil Intimidate" > pidgey6/gabite
echo -e "Способности\nTorrent Pure Blooded Swift Swim Sniper" > pidgey6/kingdra
echo -e "Возможности Overland=7\nSurface=7 Jump=1 Power=4 Intelligence=4 Amorphous=0" > pidgey6/muk
echo -e "Ходы\nAncientpower Aqua Tail Avalanche Block Body Slam Counter Double-Edge\nDragon Pulse Drill Run Dynamicpunch Earth Power Endeavor Fire Punch\nFocus Punch Fury Cutter Ice Punch Icy Wind Iron Tail Mega Kick Mega\nPunch Mud-Slap Outrage Rollout Shock Wave Seismic Toss Sleep Talk\nSnore Spite Stealth Rock Superpower Thunderpunch\nUproar" > rhydon7
echo -e "Возможности Overland=8 Surface=6 Jump=4 Power=2\nIntelligence=4 Tracker=0 Stealth=0" > umbreon9
echo -e "Способности Growl Signal\nBeam Icy Wind Encore Ice Shard Rest Aqua Ring Aurora Beam Aqua Jet\nBrine Sheer Cold Take Down Dive Aqua Tail Ice Beam Safeguard\nHail" > vullaby2/dewgong
echo -e "Ходы Body Slam Covet Defense Curl Double-Edge Fury\nCutter Gunk Shot Helping Hand Hyper Voice Icy Wind Iron Tail Last\nResort Mud-Slap Rollout Seed Bomb Shock Wave Sleep Talk Snore Super\nFang Swift Trick Water Pulse" > zigzagoon3


# часть 2
# устанавливаю права на файлы и каталоги согласно условию задания
chmod +rwx ninjask4
chmod u=r,g=,o= ninjask4/ralts
chmod u=,g=r,o=rw ninjask4/probopass
chmod 604 ninjask4/rapidash
chmod 524 pidgey6
chmod 006 pidgey6/hippowdon
chmod 571 pidgey6/gorebyss
chmod 444 pidgey6/gabite
chmod 400 pidgey6/kingdra
chmod 404 pidgey6/muk
chmod u=rx,g=wx,o=rwx pidgey6/sceptile
chmod 404 rhydon7
chmod 046 umbreon9
chmod 512 vullaby2
chmod 551 vullaby2/sharpedo
chmod u=rw,g=w,o= vullaby2/dewgong
chmod u=rwx,g=x,o=w vullaby2/munchlax
chmod 006 zigzagoon3

# часть #3

cd .. 
chmod u+w lab0/pidgey6 
ln -s lab0/umbreon9 lab0/pidgey6/mukumbreon 
chmod u-w lab0/pidgey6 
# по задания у pidgey6 в конечном итоге должны быть права rx

chmod u+r lab0/zigzagoon3 
cp lab0/zigzagoon3 lab0/ninjask4/probopasszigzagoon 
# у ninjask4 есть все права 
chmod u-r lab0/zigzagoon3

ln lab0/umbreon9 lab0/ninjask4/rapidashumbreon

ln -s vullaby2 lab0/Copy_27

chmod u+w lab0/pidgey6/sceptile 
cp -r lab0/vullaby2 lab0/pidgey6/sceptile 
chmod u-x lab0/pidgey6/sceptile

chmod u+r lab0/umbreon9 
chmod u+w lab0/vullaby2/sharpedo 
cp lab0/umbreon9 lab0/vullaby2/sharpedo 
chmod u-r lab0/umbreon9 
chmod u-w lab0/vullaby2/sharpedo

chmod u+r lab0/pidgey6/hippowdon
cat lab0/pidgey6/hippowdon lab0/pidgey6/hippowdon > lab0/umbreon9_91
chmod u-r lab0/pidgey6/hippowdon


# часть 4

cd lab0
chmod u+r ninjask4/probopass
wc -m ninjask4/* | sort -nr

ls -lR 2>&1 | grep '^-.*e$' | sort -k9,9r | head -n 4

cat ninjask4/probopass ninjask4/rapidash 2>/tmp/err.log | sort

ls -lR | grep '^-.*on' | sort -k9,9 | head -n 4

chmod u+r pidgey6/hippowdon
chmod u+r umbreon9
chmod u+r zigzagoon3
wc -m $(ls -lR | grep '^-.*e$' | grep -o '[^ ]*$') | sort -nr

cat -n rhydon7 | sort -k2,2r

chmod u-r ninjask4/probopass
chmod u-r pidgey6/hippowdon
chmod u-r umbreon9
chmod u-r zigzagoon3


# часть 5

rm umbreon9

chmod u+w pidgey6
rm pidgey6/hippowdon

rm pidgey6/mukumbreon

rm ninjask4/rapidashumbreon

rm -r pidgey6






























