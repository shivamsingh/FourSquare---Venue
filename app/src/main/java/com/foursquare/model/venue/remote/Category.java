package com.foursquare.model.venue.remote;

public class Category {
    private Icon icon;

    public Icon getIcon() {
      return icon;
    }

    public void setIcon(Icon icon) {
      this.icon = icon;
    }

    String picUrl_88() {
      return icon.getPrefix() + 88 + icon.getSuffix();
    }

    String picUrl_44() {
      return icon.getPrefix() + 44 + icon.getSuffix();
    }

    String picUrl_66() {
      return icon.getPrefix() + 66 + icon.getSuffix();
    }
  }