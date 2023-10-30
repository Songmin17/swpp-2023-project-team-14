# Generated by Django 4.1 on 2023-10-30 05:36

import django.contrib.auth.models
import django.contrib.auth.validators
from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ("auth", "0012_alter_user_first_name_max_length"),
    ]

    operations = [
        migrations.CreateModel(
            name="PersonalUser",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                (
                    "last_login",
                    models.DateTimeField(
                        blank=True, null=True, verbose_name="last login"
                    ),
                ),
                (
                    "is_superuser",
                    models.BooleanField(
                        default=False,
                        help_text="Designates that this user has all permissions without explicitly assigning them.",
                        verbose_name="superuser status",
                    ),
                ),
                (
                    "username",
                    models.CharField(
                        error_messages={
                            "unique": "A user with that username already exists."
                        },
                        help_text="Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.",
                        max_length=150,
                        unique=True,
                        validators=[
                            django.contrib.auth.validators.UnicodeUsernameValidator()
                        ],
                        verbose_name="username",
                    ),
                ),
                (
                    "first_name",
                    models.CharField(
                        blank=True, max_length=150, verbose_name="first name"
                    ),
                ),
                (
                    "last_name",
                    models.CharField(
                        blank=True, max_length=150, verbose_name="last name"
                    ),
                ),
                (
                    "is_staff",
                    models.BooleanField(
                        default=False,
                        help_text="Designates whether the user can log into this admin site.",
                        verbose_name="staff status",
                    ),
                ),
                (
                    "is_active",
                    models.BooleanField(
                        default=True,
                        help_text="Designates whether this user should be treated as active. Unselect this instead of deleting accounts.",
                        verbose_name="active",
                    ),
                ),
                (
                    "date_joined",
                    models.DateTimeField(
                        default=django.utils.timezone.now, verbose_name="date joined"
                    ),
                ),
                ("nickname", models.CharField(max_length=10)),
                ("email", models.EmailField(max_length=254, unique=True)),
                ("password", models.CharField(max_length=200)),
                (
                    "role",
                    models.CharField(
                        choices=[("User", "User"), ("Group", "Group")],
                        default="User",
                        max_length=10,
                    ),
                ),
                (
                    "major",
                    models.CharField(
                        choices=[
                            ("Business", "경영대학"),
                            ("Engineering", "공과대학"),
                            ("Art", "미술대학"),
                            ("Education", "사범대학"),
                            ("SocialSciences", "사회과학대학"),
                            ("Music", "음악대학"),
                            ("Humanities", "인문대학"),
                            ("NaturalSciences", "자연과학대학"),
                        ],
                        default="Business",
                        max_length=20,
                    ),
                ),
                (
                    "grade",
                    models.CharField(
                        choices=[
                            ("17", "17학번 이상"),
                            ("18", "18학번"),
                            ("19", "19학번"),
                            ("20", "20학번"),
                            ("21", "21학번"),
                            ("22", "22학번"),
                            ("23", "23학번"),
                        ],
                        default="23",
                        max_length=10,
                    ),
                ),
                (
                    "interest",
                    models.CharField(
                        choices=[
                            ("dance", "댄스"),
                            ("meetup", "사교"),
                            ("social", "사회"),
                            ("theater", "연극"),
                            ("music", "음악"),
                            ("sports", "운동"),
                            ("art", "예술"),
                            ("religion", "종교"),
                        ],
                        default="music",
                        max_length=10,
                    ),
                ),
                (
                    "groups",
                    models.ManyToManyField(
                        blank=True,
                        help_text="The groups this user belongs to. A user will get all permissions granted to each of their groups.",
                        related_name="user_set",
                        related_query_name="user",
                        to="auth.group",
                        verbose_name="groups",
                    ),
                ),
                (
                    "user_permissions",
                    models.ManyToManyField(
                        blank=True,
                        help_text="Specific permissions for this user.",
                        related_name="user_set",
                        related_query_name="user",
                        to="auth.permission",
                        verbose_name="user permissions",
                    ),
                ),
            ],
            options={
                "verbose_name": "user",
                "verbose_name_plural": "users",
                "abstract": False,
            },
            managers=[("objects", django.contrib.auth.models.UserManager()),],
        ),
    ]
