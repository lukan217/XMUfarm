package com.javakaihua.farmgame.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.javakaihua.farmgame.model.Crop;
import com.javakaihua.farmgame.model.Fruit;
import com.javakaihua.farmgame.view.PlayScreen;

public class NPCDialog {
	//NPC类，存储对话信息

	private Skin skin;
	private Dialog dialogOfVisitor1;
	private Dialog dialogOfVisitor2;

	private Dialog dialogOfTeacher1;
	private Dialog dialogOfTeacher2;
	private Dialog dialogOfTeacher3;

	private Dialog dialogOfSecurityGuard1;
	private Dialog dialogOfSecurityGuard2;

	private Dialog myDialog1;
	private Dialog myDialog2;
	private Dialog myDialog3;
	private Dialog myDialog4;
	private Dialog myDialog5;
	private Dialog dialogOfJudge;

	private Array<Fruit> fruitArray;

	public void setNPCDialog(String NPCName, int dialogProgress, PlayScreen screen) {
		skin = screen.getSkin();
		fruitArray=new Array<Fruit>();
		switch (NPCName) {
		case "visitor": {
			if (dialogProgress == 0) {
				dialogOfVisitor1 = new Dialog("", skin, "npc1") {
					protected void result(Object object) {
						if (object.equals(true)) {
							dialogOfVisitor1.setVisible(false);
							setMyDialog("visitor", 0, screen);
							myDialog1.show(screen.getStage());
						}
					}
				};
				dialogOfVisitor1.text("同学，请问那个湖龙福要怎么走啊？");
				dialogOfVisitor1.button("乐心帮助", true).button("拒绝请求",false);
			}

			if (dialogProgress == 1) {
				dialogOfVisitor2 = new Dialog("", skin, "npc1") {
					protected void result(Object object) {
						if (object.equals(true)) {
							Crop crop=screen.getCropArray().random();
							int lucknumber= MathUtils.random(1,5);
							crop.setCropNumber(crop.getCropNumber()+lucknumber);
							screen.getBag().update();
							new Dialog("", skin, "dialog") {
								protected void result(Object object) {
									if (object.equals(true)) {
									}
								}
							}.text("收到" + crop.getName() +"种子"+ lucknumber+"份").button("确认", true).show(screen.getStage());
							}
						dialogOfVisitor2.setVisible(false);
						}

				};
				dialogOfVisitor2.text("谢谢啊同学，厦门大学不仅风景美，这里的学生也美,我有些种子想送给你");
				dialogOfVisitor2.button("接受礼物", true);
			}
			break;
		}

		case "teacher": {
			if (dialogProgress == 0) {
				dialogOfTeacher1 = new Dialog("", skin, "npc2") {
					protected void result(Object object) {
						if (object.equals(true)) {
							dialogOfTeacher1.setVisible(false);
							setMyDialog("teacher", 0, screen);
							myDialog2.show(screen.getStage());
						}
					}
				};
				dialogOfTeacher1.text("班长，最近同学们的上课反应怎么样呢？");
				dialogOfTeacher1.button("继续对话", true);
				dialogOfTeacher1.setPosition(0, 0);
			}

			if (dialogProgress == 1) {
				dialogOfTeacher2 = new Dialog("", skin, "npc2") {
					protected void result(Object object) {
						if (object.equals(true)) {
							dialogOfTeacher2.setVisible(false);
						}
					}
				};
				dialogOfTeacher2.text("这个果实看起来不错，谢谢啦！");
				dialogOfTeacher2.button("结束对话", true);
			}
			break;
		}

		case "security": {
			if (dialogProgress == 0) {
				dialogOfSecurityGuard1 = new Dialog("", skin, "npc3") {
					protected void result(Object object) {
						if (object.equals(true)) {
							dialogOfSecurityGuard1.setVisible(false);
							setMyDialog("security", 0, screen);
							myDialog5.show(screen.getStage());
						}
					}
				};
				dialogOfSecurityGuard1.text("同学，请出示你的相关证件！");
				dialogOfSecurityGuard1.button("继续对话", true);
			}

			if (dialogProgress == 1) {
				dialogOfSecurityGuard2 = new Dialog("", skin, "npc3") {
					protected void result(Object object) {
						if (object.equals(true)) {
							dialogOfSecurityGuard2.setVisible(false);
						}
					}
				};
				dialogOfSecurityGuard2.text("好，你现在可以选择自由进出！");
				dialogOfSecurityGuard2.button("结束对话", true);
			}
			break;
		}
		}
	}

	public void setMyDialog(String NPCName, int dialogProgress, PlayScreen screen) {
		switch (NPCName) {
			case "visitor": {
				if (dialogProgress == 0) {
					myDialog1 = new Dialog("", skin, "npcme") {
						protected void result(Object object) {
							if (object.equals(true)) {
								myDialog1.setVisible(false);
								setNPCDialog("visitor", 1, screen);
								dialogOfVisitor2.show(screen.getStage());
							}
						}
					};
					myDialog1.text("这位阿姨，您说的是芙蓉湖吧，就在您面前呢！");
					myDialog1.button("继续对话", true);

				}
				break;
			}

			case "teacher": {
				if (dialogProgress == 0) {
					myDialog2 = new Dialog("", skin, "npcme") {
						protected void result(Object object) {
							if (object.equals(true)) {
								myDialog2.setVisible(false);
								setNPCDialog("teacher", 1, screen);

								fruitArray.clear();
								for (int i = 0; i < screen.getFruitArray().size; i++) {
									if (screen.getFruitArray().get(i).getFruitNumber() > 0)
										fruitArray.add(screen.getFruitArray().get(i));
								}
								if (fruitArray.size == 0) {
									new Dialog("", skin, "dialog") {
                                        protected void result(Object object) {
                                            if (object.equals(true)) {
                                                dialogOfTeacher2.setVisible(false);
                                            }
                                        }
                                    }.text("您的背包内没有果实").button("确认", "true").show(screen.getStage());
								} else {
									int num = (int) (Math.random() * fruitArray.size );
									fruitArray.get(num).setFruitNumber(fruitArray.get(num).getFruitNumber() - 1);
									screen.getBag().update();
									new Dialog("", skin, "dialog") {
										protected void result(Object object) {
											if (object.equals(true)) {
                                                dialogOfTeacher2.show(screen.getStage());
											}
										}
									}.text("成功赠送" + fruitArray.get(num).getName() + "X1").button("确认", true).show(screen.getStage());
								}
							}
						}
					};
					myDialog2.text("同学们表示上课节奏还可以。老师，最近我们的农场生产了\n一些不错的果实想送一些给您。");
					myDialog2.button("赠送礼物", true);
				}
				break;
			}

			case "security": {
				if (dialogProgress == 0) {
					myDialog5 = new Dialog("", skin, "npcme") {
						protected void result(Object object) {
							if (object.equals(true)) {
								myDialog5.setVisible(false);
								setNPCDialog("security", 1, screen);
								dialogOfSecurityGuard2.show(screen.getStage());

							}
						}
					};
					myDialog5.text("保安叔叔，这是我的厦大学生证");
					myDialog5.button("继续对话", true);
				}
				break;
			}
		}
	}
	public Dialog getDialogOfVisitor() {
		return dialogOfVisitor1;
	}

	public Dialog getDialogOfTeacher() {
		return dialogOfTeacher1;
	}

	public Dialog getDialogOfSecurityGuard() {
		return dialogOfSecurityGuard1;
	}
	public void setDialog(PlayScreen screen) {
		dialogOfJudge = new Dialog("", skin, "dialog") {
			protected void result(Object object) {
				if (object.equals(true)) {
					dialogOfJudge.setVisible(false);
				}
			}
		};
		dialogOfJudge.text("今天已经和NPC对话过了，明天早上7点之后再来吧");
		dialogOfJudge.button("我知道了", true);
		dialogOfJudge.show(screen.getStage());

	}


}
