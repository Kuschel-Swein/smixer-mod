# Smixer

Smixer is a minecraft mod that should be used together with
the [SkyblockAddons Mod by Biscuit](https://github.com/BiscuitDevelopment/SkyblockAddons). Sadly SBA is End-Of-Life and
no longer receives updates, therefore this mod exists. It fixes a lot of its bugs by using so-called "Mixins".
<br>
**A word of warning:** As this mod is in a very early stage crashes are very likely to happen, please report them in an
issue!

## Why did I choose the name "Smixer"?

Well that's a boring story. I first thought of naming it "SBAMixer", as it's a Mixin-Mod for SBA but this looked ugly in
the package names. So I just went with Smixer.

# Usage

To use this mod, first install the newest version (`v1.7.2` as of writing this) of SBA from their GitHub. Then
additionally install this mod alongside it, you should now see how many bugs "magically" disappear.
<br>
Please note, that currently the only fully supported language is english, feel free to contribute any other
translations.

# Found a bug in either sba or this mod?

If found a bug/something isn't up-to-date in SBA please create
a [Feature Request](https://github.com/Kuschel-Swein/smixer-mod/issues/new?assignees=&labels=enhancement&projects=&template=FEATURE-REQUEST.yml&title=%5BFeature%5D+).
<br>
If you found a bug in the Smixer mod itself, please create
a [Bug Report](https://github.com/Kuschel-Swein/smixer-mod/issues/new?assignees=&labels=bug&projects=&template=BUG-REPORT.yml&title=%5BBug%5D+).

# Building it yourself

To build this mod yourself you have to do the usual gradle shenanigans. Additionally, you have to download the newest
version (`v1.7.2` as of writing this) of SBA from their GitHub and place it in the `libs` folder. The name of this file
is hard-coded in the build-script.