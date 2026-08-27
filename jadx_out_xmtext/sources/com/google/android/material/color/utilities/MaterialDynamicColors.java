package com.google.android.material.color.utilities;

import java.util.function.Function;

/* loaded from: classes.dex */
public final class MaterialDynamicColors {
    private static final double CONTAINER_ACCENT_TONE_DELTA = 15.0d;

    public DynamicColor highestSurface(DynamicScheme dynamicScheme) {
        return dynamicScheme.isDark ? surfaceBright() : surfaceDim();
    }

    public DynamicColor primaryPaletteKeyColor() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda63
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda65
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).primaryPalette.getKeyColor().getTone());
            }
        });
    }

    public DynamicColor secondaryPaletteKeyColor() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda70
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda71
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).secondaryPalette.getKeyColor().getTone());
            }
        });
    }

    public DynamicColor tertiaryPaletteKeyColor() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda80
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda81
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).tertiaryPalette.getKeyColor().getTone());
            }
        });
    }

    public DynamicColor neutralPaletteKeyColor() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).neutralPalette.getKeyColor().getTone());
            }
        });
    }

    public DynamicColor neutralVariantPaletteKeyColor() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda131
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda132
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).neutralVariantPalette.getKeyColor().getTone());
            }
        });
    }

    public DynamicColor background() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda91
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda92
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 6.0d : 98.0d);
            }
        });
    }

    public DynamicColor onBackground() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda95
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda96
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 10.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda98
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m64lambda$onBackground$14$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onBackground$14$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m64lambda$onBackground$14$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return background();
    }

    public DynamicColor surface() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda64
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 6.0d : 98.0d);
            }
        });
    }

    public DynamicColor inverseSurface() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda35
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda36
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 20.0d);
            }
        });
    }

    public DynamicColor surfaceBright() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda101
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda102
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 24.0d : 98.0d);
            }
        });
    }

    public DynamicColor surfaceDim() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda33
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda44
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 6.0d : 87.0d);
            }
        });
    }

    public DynamicColor surfaceContainerLowest() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 4.0d : 100.0d);
            }
        });
    }

    public DynamicColor surfaceContainerLow() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda72
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda73
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 96.0d);
            }
        });
    }

    public DynamicColor surfaceContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda29
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda30
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 12.0d : 94.0d);
            }
        });
    }

    public DynamicColor surfaceContainerHigh() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda93
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda94
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 17.0d : 92.0d);
            }
        });
    }

    public DynamicColor surfaceContainerHighest() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda137
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda138
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 22.0d : 90.0d);
            }
        });
    }

    public DynamicColor onSurface() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda130
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda141
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 10.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor inverseOnSurface() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 20.0d : 95.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m62lambda$inverseOnSurface$37$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$inverseOnSurface$37$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m62lambda$inverseOnSurface$37$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return inverseSurface();
    }

    public DynamicColor surfaceVariant() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda128
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda129
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 90.0d);
            }
        });
    }

    public DynamicColor onSurfaceVariant() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda31
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda32
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 30.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda34
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m77lambda$onSurfaceVariant$42$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onSurfaceVariant$42$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m77lambda$onSurfaceVariant$42$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return surfaceVariant();
    }

    public DynamicColor outline() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 60.0d : 50.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor outlineVariant() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda99
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda100
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 80.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor shadow() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda135
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda136
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(0.0d);
            }
        });
    }

    public DynamicColor scrim() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda50
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda51
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(0.0d);
            }
        });
    }

    public DynamicColor surfaceTint() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 40.0d);
            }
        });
    }

    public DynamicColor primaryContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda89
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda90
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$primaryContainer$54((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    static /* synthetic */ Double lambda$primaryContainer$54(DynamicScheme dynamicScheme) {
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(performAlbers(dynamicScheme.sourceColorHct, dynamicScheme));
        }
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 85.0d : 25.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
    }

    public DynamicColor onPrimaryContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda123
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda124
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m68lambda$onPrimaryContainer$56$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda125
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m69lambda$onPrimaryContainer$57$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        }, null);
    }

    /* renamed from: lambda$onPrimaryContainer$56$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ Double m68lambda$onPrimaryContainer$56$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DynamicColor.contrastingTone(primaryContainer().tone.apply(dynamicScheme).doubleValue(), 4.5d));
        }
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 0.0d : 100.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* renamed from: lambda$onPrimaryContainer$57$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m69lambda$onPrimaryContainer$57$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return primaryContainer();
    }

    public DynamicColor primary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda47
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda48
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$primary$59((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this), new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda49
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m83lambda$primary$60$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$primary$59(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 100.0d : 0.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
    }

    /* renamed from: lambda$primary$60$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ ToneDeltaConstraint m83lambda$primary$60$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return new ToneDeltaConstraint(CONTAINER_ACCENT_TONE_DELTA, primaryContainer(), dynamicScheme.isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
    }

    public DynamicColor inversePrimary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda106
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda107
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 40.0d : 80.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda109
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m63lambda$inversePrimary$63$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$inversePrimary$63$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m63lambda$inversePrimary$63$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return inverseSurface();
    }

    public DynamicColor onPrimary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda103
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda104
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$onPrimary$65((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda105
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m67lambda$onPrimary$66$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$onPrimary$65(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 20.0d : 100.0d);
    }

    /* renamed from: lambda$onPrimary$66$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m67lambda$onPrimary$66$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return primary();
    }

    public DynamicColor secondaryContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda74
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda76
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$secondaryContainer$68((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    static /* synthetic */ Double lambda$secondaryContainer$68(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 30.0d : 85.0d);
        }
        double d = dynamicScheme.isDark ? 30.0d : 90.0d;
        if (!isFidelity(dynamicScheme)) {
            return Double.valueOf(d);
        }
        return Double.valueOf(performAlbers(dynamicScheme.secondaryPalette.getHct(findDesiredChromaByTone(dynamicScheme.secondaryPalette.getHue(), dynamicScheme.secondaryPalette.getChroma(), d, !dynamicScheme.isDark)), dynamicScheme));
    }

    public DynamicColor onSecondaryContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m73lambda$onSecondaryContainer$70$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m74lambda$onSecondaryContainer$71$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onSecondaryContainer$70$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ Double m73lambda$onSecondaryContainer$70$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DynamicColor.contrastingTone(secondaryContainer().tone.apply(dynamicScheme).doubleValue(), 4.5d));
        }
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* renamed from: lambda$onSecondaryContainer$71$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m74lambda$onSecondaryContainer$71$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return secondaryContainer();
    }

    public DynamicColor secondary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda149
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda150
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 40.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this), new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda151
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m84lambda$secondary$74$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$secondary$74$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ ToneDeltaConstraint m84lambda$secondary$74$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return new ToneDeltaConstraint(CONTAINER_ACCENT_TONE_DELTA, secondaryContainer(), dynamicScheme.isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
    }

    public DynamicColor onSecondary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda146
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda147
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$onSecondary$76((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda148
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m72lambda$onSecondary$77$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$onSecondary$76(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 10.0d : 100.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 20.0d : 100.0d);
    }

    /* renamed from: lambda$onSecondary$77$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m72lambda$onSecondary$77$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return secondary();
    }

    public DynamicColor tertiaryContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda144
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda145
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$tertiaryContainer$79((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    static /* synthetic */ Double lambda$tertiaryContainer$79(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 60.0d : 49.0d);
        }
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DislikeAnalyzer.fixIfDisliked(dynamicScheme.tertiaryPalette.getHct(performAlbers(dynamicScheme.tertiaryPalette.getHct(dynamicScheme.sourceColorHct.getTone()), dynamicScheme))).getTone());
        }
        return Double.valueOf(dynamicScheme.isDark ? 30.0d : 90.0d);
    }

    public DynamicColor onTertiaryContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m79lambda$onTertiaryContainer$81$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m80lambda$onTertiaryContainer$82$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onTertiaryContainer$81$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ Double m79lambda$onTertiaryContainer$81$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 0.0d : 100.0d);
        }
        if (isFidelity(dynamicScheme)) {
            return Double.valueOf(DynamicColor.contrastingTone(tertiaryContainer().tone.apply(dynamicScheme).doubleValue(), 4.5d));
        }
        return Double.valueOf(dynamicScheme.isDark ? 90.0d : 10.0d);
    }

    /* renamed from: lambda$onTertiaryContainer$82$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m80lambda$onTertiaryContainer$82$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return tertiaryContainer();
    }

    public DynamicColor tertiary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda56
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda57
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$tertiary$84((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this), new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda58
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m85lambda$tertiary$85$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$tertiary$84(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 90.0d : 25.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 80.0d : 40.0d);
    }

    /* renamed from: lambda$tertiary$85$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ ToneDeltaConstraint m85lambda$tertiary$85$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return new ToneDeltaConstraint(CONTAINER_ACCENT_TONE_DELTA, tertiaryContainer(), dynamicScheme.isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
    }

    public DynamicColor onTertiary() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda97
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda108
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$onTertiary$87((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda119
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m78lambda$onTertiary$88$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$onTertiary$87(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
        }
        return Double.valueOf(dynamicScheme.isDark ? 20.0d : 100.0d);
    }

    /* renamed from: lambda$onTertiary$88$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m78lambda$onTertiary$88$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return tertiary();
    }

    public DynamicColor errorContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda42
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).errorPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda43
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 90.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor onErrorContainer() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda37
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).errorPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda38
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 90.0d : 10.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda39
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m66lambda$onErrorContainer$93$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onErrorContainer$93$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m66lambda$onErrorContainer$93$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return errorContainer();
    }

    public DynamicColor error() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda24
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).errorPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 40.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this), new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m61lambda$error$96$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$error$96$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ ToneDeltaConstraint m61lambda$error$96$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return new ToneDeltaConstraint(CONTAINER_ACCENT_TONE_DELTA, errorContainer(), dynamicScheme.isDark ? TonePolarity.DARKER : TonePolarity.LIGHTER);
    }

    public DynamicColor onError() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda117
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).errorPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda118
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 20.0d : 100.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda120
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m65lambda$onError$99$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onError$99$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m65lambda$onError$99$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return error();
    }

    public DynamicColor primaryFixed() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda139
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda140
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$primaryFixed$101((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    static /* synthetic */ Double lambda$primaryFixed$101(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 100.0d : 10.0d);
        }
        return Double.valueOf(90.0d);
    }

    public DynamicColor primaryFixedDim() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda142
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda143
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$primaryFixedDim$103((DynamicScheme) obj);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    static /* synthetic */ Double lambda$primaryFixedDim$103(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 90.0d : 20.0d);
        }
        return Double.valueOf(80.0d);
    }

    public DynamicColor onPrimaryFixed() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda21
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$onPrimaryFixed$105((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda23
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m70lambda$onPrimaryFixed$106$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$onPrimaryFixed$105(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 10.0d : 90.0d);
        }
        return Double.valueOf(10.0d);
    }

    /* renamed from: lambda$onPrimaryFixed$106$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m70lambda$onPrimaryFixed$106$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return primaryFixedDim();
    }

    public DynamicColor onPrimaryFixedVariant() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda112
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda113
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MaterialDynamicColors.lambda$onPrimaryFixedVariant$108((DynamicScheme) obj);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda114
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m71lambda$onPrimaryFixedVariant$109$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    static /* synthetic */ Double lambda$onPrimaryFixedVariant$108(DynamicScheme dynamicScheme) {
        if (isMonochrome(dynamicScheme)) {
            return Double.valueOf(dynamicScheme.isDark ? 30.0d : 70.0d);
        }
        return Double.valueOf(30.0d);
    }

    /* renamed from: lambda$onPrimaryFixedVariant$109$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m71lambda$onPrimaryFixedVariant$109$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return primaryFixedDim();
    }

    public DynamicColor secondaryFixed() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda110
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda111
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 80.0d : 90.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor secondaryFixedDim() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda126
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda127
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 70.0d : 80.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor onSecondaryFixed() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(10.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m75lambda$onSecondaryFixed$116$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onSecondaryFixed$116$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m75lambda$onSecondaryFixed$116$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return secondaryFixedDim();
    }

    public DynamicColor onSecondaryFixedVariant() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda52
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).secondaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda53
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 25.0d : 30.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda54
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m76lambda$onSecondaryFixedVariant$119$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onSecondaryFixedVariant$119$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m76lambda$onSecondaryFixedVariant$119$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return secondaryFixedDim();
    }

    public DynamicColor tertiaryFixed() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda45
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda46
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 40.0d : 90.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor tertiaryFixedDim() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda115
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda116
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 30.0d : 80.0d);
            }
        }, new MaterialDynamicColors$$ExternalSyntheticLambda152(this));
    }

    public DynamicColor onTertiaryFixed() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda77
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda78
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 90.0d : 10.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda79
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m81lambda$onTertiaryFixed$126$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onTertiaryFixed$126$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m81lambda$onTertiaryFixed$126$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return tertiaryFixedDim();
    }

    public DynamicColor onTertiaryFixedVariant() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda67
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).tertiaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda68
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(MaterialDynamicColors.isMonochrome((DynamicScheme) obj) ? 70.0d : 30.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda69
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m82lambda$onTertiaryFixedVariant$129$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors((DynamicScheme) obj);
            }
        });
    }

    /* renamed from: lambda$onTertiaryFixedVariant$129$com-google-android-material-color-utilities-MaterialDynamicColors, reason: not valid java name */
    /* synthetic */ DynamicColor m82lambda$onTertiaryFixedVariant$129$comgoogleandroidmaterialcolorutilitiesMaterialDynamicColors(DynamicScheme dynamicScheme) {
        return tertiaryFixedDim();
    }

    public DynamicColor controlActivated() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda61
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).primaryPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda62
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 90.0d);
            }
        }, null);
    }

    public DynamicColor controlNormal() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda59
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda60
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 80.0d : 30.0d);
            }
        });
    }

    public DynamicColor controlHighlight() {
        return new DynamicColor(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda82
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(0.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda83
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(0.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda84
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 100.0d : 0.0d);
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda85
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 0.2d : 0.12d);
            }
        }, null, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda87
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(DynamicColor.toneMinContrastDefault(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda66
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return Double.valueOf(((DynamicScheme) obj2).isDark ? 100.0d : 0.0d);
                    }
                }, null, (DynamicScheme) obj, null));
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda88
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(DynamicColor.toneMaxContrastDefault(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda55
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        return Double.valueOf(((DynamicScheme) obj2).isDark ? 100.0d : 0.0d);
                    }
                }, null, (DynamicScheme) obj, null));
            }
        }, null);
    }

    public DynamicColor textPrimaryInverse() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda27
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda28
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            }
        });
    }

    public DynamicColor textSecondaryAndTertiaryInverse() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda40
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralVariantPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda41
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 30.0d : 80.0d);
            }
        });
    }

    public DynamicColor textPrimaryInverseDisableOnly() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda121
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda122
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            }
        });
    }

    public DynamicColor textSecondaryAndTertiaryInverseDisabled() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda75
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda86
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            }
        });
    }

    public DynamicColor textHintInverse() {
        return DynamicColor.fromPalette(new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda133
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DynamicScheme) obj).neutralPalette;
            }
        }, new Function() { // from class: com.google.android.material.color.utilities.MaterialDynamicColors$$ExternalSyntheticLambda134
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Double.valueOf(((DynamicScheme) obj).isDark ? 10.0d : 90.0d);
            }
        });
    }

    private static ViewingConditions viewingConditionsForAlbers(DynamicScheme dynamicScheme) {
        return ViewingConditions.defaultWithBackgroundLstar(dynamicScheme.isDark ? 30.0d : 80.0d);
    }

    private static boolean isFidelity(DynamicScheme dynamicScheme) {
        return dynamicScheme.variant == Variant.FIDELITY || dynamicScheme.variant == Variant.CONTENT;
    }

    private static boolean isMonochrome(DynamicScheme dynamicScheme) {
        return dynamicScheme.variant == Variant.MONOCHROME;
    }

    static double findDesiredChromaByTone(double d, double d2, double d3, boolean z) {
        Hct hctFrom = Hct.from(d, d2, d3);
        if (hctFrom.getChroma() < d2) {
            double chroma = hctFrom.getChroma();
            while (hctFrom.getChroma() < d2) {
                d3 += z ? -1.0d : 1.0d;
                Hct hctFrom2 = Hct.from(d, d2, d3);
                if (chroma > hctFrom2.getChroma() || Math.abs(hctFrom2.getChroma() - d2) < 0.4d) {
                    return d3;
                }
                if (Math.abs(hctFrom2.getChroma() - d2) < Math.abs(hctFrom.getChroma() - d2)) {
                    hctFrom = hctFrom2;
                }
                chroma = Math.max(chroma, hctFrom2.getChroma());
            }
        }
        return d3;
    }

    static double performAlbers(Hct hct, DynamicScheme dynamicScheme) {
        Hct hctInViewingConditions = hct.inViewingConditions(viewingConditionsForAlbers(dynamicScheme));
        if (DynamicColor.tonePrefersLightForeground(hct.getTone()) && !DynamicColor.toneAllowsLightForeground(hctInViewingConditions.getTone())) {
            return DynamicColor.enableLightForeground(hct.getTone());
        }
        return DynamicColor.enableLightForeground(hctInViewingConditions.getTone());
    }
}
